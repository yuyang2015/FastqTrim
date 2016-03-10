FastqTrim
===
A fastq file trim tool for high throughput sequence data by Spark.
---
### Introduction
target : input the fastqFile then 'get rid of the unqualified record' or 'correct the unqualified record'.<br>
* 1. get rid of the unqualified record - > record that the first quality-value is less than the standard quality-values
* 2. correct the unqualified record -> foreach the record quality-value until meet an unqualified quality-value,
<br>return the qualified part(the length of qualified part need to be greater than 30 , or get rid of it)

####how to use it
* download the source code to your workspace (recommend IntelliJ IDEA)
* build artifacts(jar) e.g. In IDEA you can accomplish it by follow 4 steps:<br> `①Bulid->②Build Artifacts->③fastqTrim->④Build`
* submit the fastqTrim jar on your Spark cluster like :<br>
`spark-submit --master spark://bio-server:7077 --class com.celloud.fastqTrim.Entry.fastqTrimGo /share/data/yuyang/jars/fastqTrim.jar /home/yuyang/data/2000w_single.fastq /share/data/yuyang/jars/test3 hdfs://bio-server:9000` <br>the 3 params expressed as <br>
p1: path of input file from hdfs e.g. /data/mydata/trimFile.fastq<br>
p2: hdfs or local-share-storage path of output file e.g. /data/mydata/output<br>
p3: hdfs master uri e.g. hdfs://master:9000<br>

####display the result
#####fastq file before FastqTrim<br>
　`@ST-E00294:19:H53C3CCXX:7:2224:5598:73388 1:N:0:CTCAGA`<br>
　`AGGTTGACCACATTGAGATGGTGCCAGCAATAGATGCTGGAATTCTCGNNNGCCNNGGAACNNNNNNNNNNNNNNNNNNNNNNNTGNCGTCTTCTGCTTGAAAAAAAAAA...`<br>
　`+`<br>
　`AAFFFKKKKKKKKKFFFFKKKKKKKKKKKKKKKKKKKKKKKKKKFKKK###FAF##KKKKF#######################AK#AKKFKKAFKFKKKFFKKKKKKKK...`<br>
　`@ST-E00294:19:H53C3CCXX:7:2224:13149:73388 1:N:0:CTCAGA`<br>
　`AGGTTGACCACATTGAGATGGTGCCAGCAATAGATGCTGGAATTNTCGNNNGCCNNGGAANNNNNNNNNNNNNNNNNNNNNNNNTGNCGTCNNCTGCTTGAAAAAAAAAA...`<br>
　`+`<br>
　`AAFFFKKKKKKKKKKFKKKKKKKKKKKKKKKKKKKKKKKKKKKK#KKK###KFK##KKKK########################KK#KKKK##KKKKKKKKKKKKKKKKK...`<br>
　`...`<br>
#####fastq file after FastqTrim<br>
　`@ST-E00294:19:H53C3CCXX:7:2224:5598:73388 1:N:0:CTCAGA`<br>
　`AGGTTGACCACATTGAGATGGTGCCAGCAATAGATGCTGGAATT`<br>
　`+`<br>
　`AAFFFKKKKKKKKKKFKKKKKKKKKKKKKKKKKKKKKKKKKKKK`<br>
　`@ST-E00294:19:H53C3CCXX:7:2224:13149:73388 1:N:0:CTCAGA`<br>
　`AGGTTGACCACATTGAGATGGTGCCAGCAATAGATGCTGGAATT`<br>
　`+`<br>
　`AAFFFKKKKKKKKKKFKKKKKKKKKKKKKKKKKKKKKKKKKKKK`<br>
　`...`<br>
