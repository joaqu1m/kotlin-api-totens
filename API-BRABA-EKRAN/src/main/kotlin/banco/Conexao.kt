package banco

import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

class Conexao {
    fun getJdbcTemplate(i: Boolean): JdbcTemplate {
        val banco = if (i) {
            listOf<String>(
                "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://dbekran.database.windows.net;databaseName=dbeKran",
                "eKranAdm",
                "1sis@grupo6"
            )
        } else {
            listOf<String>("org.h2.Driver", "jdbc:h2:./banco-aula", "sa", "")
        }
        val dataSource = BasicDataSource()
        dataSource.driverClassName = banco[0]
        dataSource.url = banco[1]
        dataSource.username = banco[2]
        dataSource.password = banco[3]

        return JdbcTemplate(dataSource)
    }
}