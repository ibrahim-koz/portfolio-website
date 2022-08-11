class OrSpecification<T>(private val left: ISpecification<T>, private val right: ISpecification<T>) :
    Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return left.isSatisfiedBy(candidate) || right.isSatisfiedBy(candidate)
    }
}