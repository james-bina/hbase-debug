package aaim.hbase

import org.apache.hadoop.hbase.{HTableDescriptor, TableName, HColumnDescriptor}
import org.apache.hadoop.hbase.HConstants.FOREVER
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm

case class ColumnFamilyDefinition(colName: String,
                                  numVersions: Int = 3,
                                  compAlg: Algorithm = Algorithm.SNAPPY,
                                  inMemoryFlag: Boolean = false,
                                  timeToLive: Int = FOREVER)

class Focus(val name: String) {

  val tableName = TableName.valueOf("v2", name)

  val familyDefinitions = Array(ColumnFamilyDefinition("indices", inMemoryFlag = true),
    ColumnFamilyDefinition("itrees", inMemoryFlag = true))

  val columnDescriptors: Array[HColumnDescriptor] = familyDefinitions map {
    c =>
      val desc = new HColumnDescriptor(c.colName)
      desc.setCompressionType(c.compAlg)
        .setCompactionCompressionType(c.compAlg)
        .setInMemory(c.inMemoryFlag)
        .setMinVersions(c.numVersions)
        .setMaxVersions(c.numVersions)
        .setTimeToLive(c.timeToLive)
  }

  val tableDescriptor = {
    val ht = new HTableDescriptor(tableName)
    columnDescriptors foreach { cd => ht.addFamily(cd) }
    ht
  }

}
