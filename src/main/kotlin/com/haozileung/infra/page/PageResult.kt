package com.haozileung.infra.page

import com.google.common.collect.Lists
import java.io.Serializable

/**
 * Created by haozi on 16-3-9.
 */
class PageResult<T> (var total: Long? = 0L,
                     var rows: List<T> = Lists.newArrayList<T>()): Serializable {

}
