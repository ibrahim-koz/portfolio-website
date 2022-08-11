class NotSpecification<T>(private val specification: ISpecification<T>) :
    Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return !specification.isSatisfiedBy(candidate)
    }
}