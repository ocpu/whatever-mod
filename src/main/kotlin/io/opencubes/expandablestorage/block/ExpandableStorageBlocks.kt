package io.opencubes.expandablestorage.block

object ExpandableStorageBlocks {
  val cache = Cache()
  val pipe = BlockPipe()

  val all = arrayOf(
      cache,
      pipe
  )
}

