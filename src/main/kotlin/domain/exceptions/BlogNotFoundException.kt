package domain.exceptions

import model.Id

class BlogNotFoundException(val id: Id): Exception()