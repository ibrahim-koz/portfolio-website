interface ISpecification<T> {
    fun and(other: ISpecification<T>): ISpecification<T>
    fun or(other: ISpecification<T>): ISpecification<T>
    fun not(): ISpecification<T>
    fun isSatisfiedBy(candidate: T): Boolean
}