abstract class Specification<T> : ISpecification<T> {
    override fun and(other: ISpecification<T>): ISpecification<T> {
        return AndSpecification(this, other)
    }

    override fun or(other: ISpecification<T>): ISpecification<T> {
        return OrSpecification(this, other)
    }

    override fun not(): ISpecification<T> {
        return NotSpecification(this)
    }
}