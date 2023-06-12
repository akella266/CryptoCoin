package ru.akella.cryptocoin.domain

class NotFoundException(val e: Throwable?) : Exception()
class BadRequestException(val e: Throwable?) : Exception()
class UnauthorizedException(val e: Throwable?) : Exception()
class ForbiddenException(val e: Throwable?) : Exception()
class TooManyRequestsException(val e: Throwable?) : Exception()
class ServerException(val e: Throwable?) : Exception()