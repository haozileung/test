package com.haozileung.infra.utils

import net.sf.ehcache.CacheManager


/**
 * Created by Efun on 2016/3/22.
 */
object EhcacheUtil{
    val instance = CacheManager.create();
}
