import java.time.LocalDate

class Product(
    private val name: String,
    val price: Int,
    private val expireDate: LocalDate
)
