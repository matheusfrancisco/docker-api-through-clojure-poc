(ns tar
  (:require
   [clojure.java.io :as io]
   [code :refer [csv-input submitted-code-snippet]])
  (:import
   [java.io ByteArrayInputStream ByteArrayOutputStream]
   [java.nio.charset StandardCharsets]
   [java.util.zip GZIPInputStream GZIPOutputStream]
   (org.apache.commons.compress.archivers.tar TarArchiveEntry TarArchiveInputStream TarArchiveOutputStream)))

(defn create-tar-gz-in-memory
  "Creates a .tar.gz file in memory. `files` is a map of file names to their string contents."
  [files]
  (let [baos (ByteArrayOutputStream.)
        gzos (GZIPOutputStream. baos)
        taos (TarArchiveOutputStream. gzos)]
    (doseq [[filename content] files]
      (let [entry (TarArchiveEntry. filename)
            content-bytes (.getBytes content "UTF-8")]
        (.setSize entry (count content-bytes))
        (.putArchiveEntry taos entry)
        (.write taos content-bytes)
        (.closeArchiveEntry taos)))
    (.finish taos)
    (finally
      (.close taos)
      (.close gzos)
      (.close baos))
    (.toByteArray baos)))

(def tarcode (create-tar-gz-in-memory {"solution.clj" submitted-code-snippet
                                       "in.csv" csv-input}))

;write tarcode to file
(defn write-tarcode-to-file [tarcode]
  (with-open [out (io/output-stream "src2.tar.gz")]
    (.write out tarcode)))
#_(write-tarcode-to-file tarcode)

(defn untar-gz-in-memory-and-decode
  "Untars a .tar.gz byte array `tar-gz-bytes`, decodes contents using UTF-8, and returns a map of file paths to String contents."
  [tar-gz-bytes]
  (with-open [bais (ByteArrayInputStream. tar-gz-bytes)
              gzis (GZIPInputStream. bais)
              tais (TarArchiveInputStream. gzis)]
    (let [entries (atom {})]
      (loop []
        (let [entry (.getNextTarEntry tais)]
          (when entry
            (let [entry-name (.getName entry)]
              (when-not (.isDirectory entry)
                (let [baos (ByteArrayOutputStream.)
                      buffer (byte-array 1024)] ; buffer size for reading
                  (loop []
                    (let [len (.read tais buffer)]
                      (when (pos? len)
                        (.write baos buffer 0 len)
                        (recur))))
                  (let [content-string (String. (.toByteArray baos) StandardCharsets/UTF_8)]
                    (swap! entries assoc entry-name content-string)))))
            (recur))))
      @entries)))

(untar-gz-in-memory-and-decode tarcode)
