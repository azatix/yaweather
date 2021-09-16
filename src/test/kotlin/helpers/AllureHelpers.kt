package helpers

import io.qameta.allure.Allure

object AllureHelpers {

    @JvmStatic
    fun <T> step(name: String, block: () -> T): T = Allure.step(name, block)

    @JvmStatic
    fun KStep(name: String, block: () -> Unit) {
        Allure.step(name, block)
    }

    fun KStep(name: String) {
        Allure.step(name)
    }
}
