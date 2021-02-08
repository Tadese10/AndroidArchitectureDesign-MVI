package com.tadese_teejay.api.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tadese_teejay.utils.Constants
import kotlinx.coroutines.*

abstract class NetworkBoundResourceWithEvent <ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataStateWithEvent<ViewStateType>>()

    init {
        result.value = DataStateWithEvent.loading(true)


        GlobalScope.launch(Dispatchers.IO){
            delay(Constants.TESTING_NETWORK_DELAY)

            withContext(Dispatchers.Main){
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }
        }
    }

    fun handleNetworkCall(response: GenericApiResponse<ResponseObject>){

        when(response){
            is ApiSuccessResponse ->{
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse ->{
                println("DEBUG: NetworkBoundResourceWithEvent: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse ->{
                println("DEBUG: NetworkBoundResourceWithEvent: Request returned NOTHING (HTTP 204)")
                onReturnError("HTTP 204. Returned NOTHING.")
            }
        }
    }

    fun onReturnError(message: String){
        result.value = DataStateWithEvent.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataStateWithEvent<ViewStateType>>
}
