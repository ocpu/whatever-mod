package io.opencubes.expandablestorage.proxy

interface IProxy {
  fun preInit() {}
  fun init() {}
  fun postInit() {}
}

class CommonProxy : IProxy
class ClientProxy : IProxy
