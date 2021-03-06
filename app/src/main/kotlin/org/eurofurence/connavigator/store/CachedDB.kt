package org.eurofurence.connavigator.store

import org.eurofurence.connavigator.util.extensions.lt

/**
 * The cached DB delegates to a backend, on setting, the cache will be replaced, on
 * getting it will be loaded or reused.
 */
fun <T> DB<T>.cached() = let {
    object : DB<T> {
        var cacheTime: Long? = null
        var cache: Iterable<T>? = null

        override fun delete() {
            it.delete()
            cacheTime = null
            cache = null
        }

        /**
         * Returns the time of the backed cache
         */
        override val time: Long?
            get() = it.time

        override var items: Iterable<T>
            get() = if (cacheTime lt time) {
                // Assign the cache if not yet assigned or invalidated by time
                cache = it.items
                cacheTime = time
                cache!!
            } else if (time == null)
                emptyList()
            else
                cache!!

            set(values) {
                // Overwrite the cache, reset memorized time
                cache = values
                it.items = values
                cacheTime = time
            }

    }
}

/**
 * The cached DB delegates to a backend, on setting, the cache will be replaced, on
 * getting it will be loaded or reused.
 */
fun <T> IDB<T>.cached() = let {
    object : IDB<T>() {
        override fun id(item: T): Any = it.id(item)

        var cacheTime: Long? = null

        var cache: Map<Any, T>? = null

        override fun delete() {
            it.delete()
            cacheTime = null
            cache = null
        }

        /**
         * Returns the time of the backed cache
         */
        override val time: Long?
            get() = it.time

        override var keyValues: Map<Any, T>
            get() = if (cacheTime lt time) {
                // Assign the cache if not yet assigned or invalidated by time
                cache = it.keyValues
                cacheTime = time
                cache!!
            } else if (time == null)
                emptyMap()
            else
                cache!!
            set(values) {
                // Overwrite the cache, reset memorized time
                cache = values
                it.keyValues = values
                cacheTime = time
            }

    }
}