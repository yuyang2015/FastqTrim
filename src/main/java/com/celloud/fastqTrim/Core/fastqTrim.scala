package com.celloud.fastqTrim.Core

import com.celloud.fastqTrim.QcInputFormat.CustomInputFormat
import com.celloud.fastqTrim.Utils.{Constant, fastqTrimUtil}
import org.apache.hadoop.io.Text
import org.apache.spark.{SparkConf, SparkContext}

/**
 *      target : input the fastqFile then 'get rid of the unqualified record' or 'correct the unqualified record'.
 *      1. get rid of the unqualified record - > record that the first quality-value is less than the standard quality-values
 *      2. correct the unqualified record -> foreach the record quality-value until meet an unqualified quality-value,
 *      return the qualified part(the length of qualified part need to be greater than 30 , or get rid of it)
 */

object fastqTrim {

  def doTrim(inputPath: String, outputPath: String ,hdfsMasterUri:String): Unit = {
    val name = "fastqTrimv1.3.3"
    val sc = new SparkContext(new SparkConf().setAppName(name))
    val file = sc.newAPIHadoopFile[Text, Text, CustomInputFormat](hdfsMasterUri+inputPath)
    val standCoreChar = fastqTrimUtil.getStandardScore(file)
      if(standCoreChar != 0){
        file.map(x=>x._2.toString).map(record=>{
          val array = record.split(Constant.tabEscape)
          val scoreLineArrayPair = array(3).toCharArray
          var lastCharPair=""
          var secondCharPair=""
          var flagPair = true
          var counterPair1 = 0

          while(flagPair && counterPair1 < scoreLineArrayPair.length){
            if(scoreLineArrayPair(counterPair1) - standCoreChar < Constant.core_standard){
              if(counterPair1 != 0 && counterPair1 > Constant.len_standard){
                lastCharPair = scoreLineArrayPair.subSequence(0,counterPair1).toString
                secondCharPair = array(1).toCharArray.subSequence(0,counterPair1).toString
              }
              flagPair = false
            }else if(counterPair1 == scoreLineArrayPair.length-1){
              lastCharPair = scoreLineArrayPair.subSequence(0,counterPair1+1).toString
              secondCharPair = array(1).toCharArray.subSequence(0,counterPair1+1).toString
              flagPair = false
            }else{
              counterPair1 = counterPair1 + 1
            }
          }
          if(lastCharPair.equals(""))  "!!" else array(0)+"\n"+secondCharPair+"\n"+"+"+"\n"+lastCharPair
        }).filter(line => !line.startsWith("!!")).coalesce(1,true).saveAsTextFile(outputPath)
        sc.stop()
      }
  }
}