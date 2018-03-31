package com.devchain.qproxy.converters

protected trait OptConverter {

  trait Functor[C[_]] {
    def fmap[A,B](x: C[A], f: A => B): C[B]
  }

  object Functor {
    implicit object optionFunctor extends Functor[Option] {
      override def fmap[A,B](x: Option[A], f: A => B): Option[B] = x map f
    }

    implicit object iterableFunctor extends Functor[Iterable] {
      override def fmap[A,B](x: Iterable[A], f: A => B): Iterable[B] = x map f
    }
  }

  implicit def liftConversion[F[_], A, B](x: F[A])(implicit f: A => B, functor: Functor[F]): F[B] = functor.fmap(x,f)

}
