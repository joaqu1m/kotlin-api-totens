package app
import banco.Conexao
import banco.Query
import com.github.britooo.looca.api.core.Looca
import java.time.LocalDate
import java.time.LocalTime
import java.io.File

private fun main() {
    val cursor = Query(Conexao().getJdbcTemplate(true))

    val looca = Looca()
    val particao: File = if (looca.sistema.sistemaOperacional == "Windows") {File("C:");} else {File("/")}

    while(true) {
        val disco = 100 - ((particao.freeSpace.toDouble()/1024/1024/1024) * 100 / (looca.grupoDeDiscos.discos[0].tamanho.toDouble()/1024/1024/1024))
        val ram = 100 - ((looca.memoria.disponivel.toDouble()/1024/1024/1024) * 100 / (looca.memoria.total.toDouble()/1024/1024/1024))
        val valoresPadroes = mutableListOf<Double>(looca.processador.uso, disco, ram)

        var data = "${LocalDate.now()}"
        data = data.substring(0, 4) + "/" + data.substring(5, 7) + "/" + data.substring(8, 10)
        val hora = LocalTime.now().toString().substring(0, 8)

        val lista = mutableListOf("50000")
        for (f in 0 until valoresPadroes.size) {
            val formatado = String.format("%.2f", valoresPadroes[f])
            val index = formatado.indexOf(",")
            lista += if (index != -1) {
                formatado.substring(0, index) + "." + formatado.substring(index + 1)
            } else {
                formatado
            }
        }
        lista += hora
        lista += data

        cursor.insert(lista)

        Thread.sleep(5000)
    }
}