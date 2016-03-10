package com.celloud.fastqTrim.Entry

import com.celloud.fastqTrim.Core.fastqTrim

object fastqTrimGo {

  def main(args: Array[String]): Unit = {
    assert(args.length >= 3,"Requires three parameters: p1:path of input file from hdfs " +
      "like '/data/mydata/qcFile.fastq'; p2: hdfs or local-share-storage path of output file " +
      "like '/data/mydata/output'; p3:hdfs Master Uri like 'hdfs://master:9000'")
    val inputPath = args(0)
    val outputPath = args(1)
    val hdfsMasterUri = args(2)
    fastqTrim.doTrim(inputPath,outputPath,hdfsMasterUri)
  }
}
