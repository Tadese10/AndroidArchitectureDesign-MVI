package com.tadese_teejay.api.common

import com.tadese_teejay.utils.Event

class DataStateWithEvent <T>(
    var message: String? = null,
    var loading: Boolean = false,
    var data: Event<T>? = null
)
{
    companion object {

        fun <T> error(
            message: String
        ): DataStateWithEvent<T> {
            return DataStateWithEvent(
                message = message,
                loading = false,
                data = null
            )
        }

        fun <T> loading(
            isLoading: Boolean
        ): DataStateWithEvent<T> {
            return DataStateWithEvent(
                message = null,
                loading = isLoading,
                data = null
            )
        }

        fun <T> data(
            message: String? = null,
            data: T? = null
        ): DataStateWithEvent<T> {
            return DataStateWithEvent(
                message = (message),
                loading = false,
                data = Event.dataEvent(
                    data = data
                )
            )
        }
    }

    override fun toString(): String {
        return "DataStateWithEvent(message=$message,loading=$loading,data=$data)"
    }
}