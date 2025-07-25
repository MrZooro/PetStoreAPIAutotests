package extensions

/**
 * Выполняет логирование и выполнение шага теста с поддержкой корутин.
 * Предназначен для структурирования тестовых сценариев на логические шаги.
 *
 * @param [description] Описание шага теста (будет выведено в лог)
 * @param [block] Suspend-лямбда с тестовыми действиями для выполнения
 */
suspend fun step(description: String, block: suspend() -> Unit) {
    println("STEP: $description")
    block()
}