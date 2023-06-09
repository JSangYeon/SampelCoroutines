package jsy.test.sampelcoroutines.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {

    //Coroutines 정리 - https://velog.io/@rhkswls98/Android-Kotlin-Coroutine-%EC%A0%95%EB%A6%AC
    //Flow 내용 참고 - https://medium.com/hongbeomi-dev/kotlin-coroutine-flow-ac07cfdca42d

    //runBlocking의 경우 직접 코루틴 스코프를 생성함 - Suspend가 붙지 않아도 문제가 없음, 호출 시 CoroutineScope를 사용하지 않아도 됨 Dispatcher.Main or Default인듯
    fun runNumbersFlow() = runBlocking<Unit> {
        // Flow를 생성합니다.
        val numbers = countNumbers()
        // Flow에서 방출되는 데이터를 수신합니다.
        numbers.collect {
            // 데이터를 출력합니다.
            println(it)
        }
    }

    // Flow를 생성하는 함수입니다.
    private fun countNumbers(): Flow<Int> = flow {
        // 초기값을 설정합니다.
        var i = 0
        // 무한 루프를 실행합니다.
        while (true) {
            // Flow에서 데이터를 방출합니다.
            emit(i++)
            // 1초 동안 일시 중지합니다.
            delay(1000)
        }
    }


    //coolect는 Flow를 중단하는 함수이므로, suspend가 붙어야함
    suspend fun printNumbers() {
        getNumbers().collect { num ->
            println(num)
        }
    }

    private fun getNumbers(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000)
            emit(i)
        }

        doSomethingInt {
            val testInt = getInt() //매개변수인 Int값 필요
            Log.d("doSomethingInt","$testInt")
        }

        doSomething {
            it*3
        }

        doSomethingInteger {
//            it*3 // 126 return

            5// 5 return
        }
    }


    private fun getInt(): Int {
        return 42
    }
    private fun doSomethingInt(intCallback : () -> Int ){ // Int가 매개변수
        intCallback()
    }


    /* Int는 입력값, 반환값은 없음 */
    // Unit은 반환값이 없음!  callback에 (Int)를 태우는 function
    private fun doSomething(intCallback : (Int) -> Unit) {
        val result = intCallback(42)
        Log.d("doSomething","$result")
    }

    private fun doSomethingInteger(intCallback: (Int) -> Int) {
        val result = intCallback(42) // 42 Return 이후 콜백 받은 부분에서 처리한 Int값으로 result 출력됨
        Log.d("doSomethingInteger","$result")
    }
}