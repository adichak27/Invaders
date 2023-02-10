//import javalib.funworld.*;
//import javalib.worldimages.*;
//import java.awt.Color;
//import java.awt.color.*;
//
//interface IPred<X> {
//  boolean apply(X x);
//}
//
//interface IFunc<X, Y> {
//  Y apply(X x);
//}
//
//interface IFunc2<X, Y, Z> {
//  Z apply(X x, Y y);
//}
//
//interface IFunc3<X> {
//  X apply(int i);
//}
//
//
//interface IList<T> {
//  //filter this list by the given predicate
//  IList<T> filter(IPred<T> pred);
//  //returns true if at least one member of this list passes the predicate
//  boolean ormap(IPred<T> pred);
//  //returns true if all members of this list pass the predicate
//  boolean andmap(IPred<T> pred);
//  //maps a function onto every member of this list
//  <Y> IList<Y> map(IFunc<T, Y> fun);
//  //combines the items in this list using the given function
//  <Y> Y fold(IFunc2<T, Y, Y> fun, Y base);
//}
//
//class MtList<T> implements IList<T> {
//
//  public IList<T> filter(IPred<T> pred) {
//    return this;
//  }
//  
//  public <Y> IList<Y> map(IFunc<T, Y> fun) {
//    return new MtList<Y>();
//  }
//
//  public <U> U fold(IFunc2<T, U, U> fun, U base) {
//    return base;
//  }
//
//  public boolean ormap(IPred<T> pred) {
//    return false;
//  }
//
//  public boolean andmap(IPred<T> pred) {
//    return true;
//  }
//}
//
//class ConsList<T> implements IList<T> {
//  T first;
//  IList<T> rest;
//  
//  ConsList(T first, IList<T> rest) {
//    this.first = first;
//    this.rest = rest;
//  }
//
//  public IList<T> filter(IPred<T> pred) {
//    if (pred.apply(this.first)) {
//      return new ConsList<T>(this.first, this.rest.filter(pred));
//    }
//    else {
//      return this.rest.filter(pred);
//    }
//  }
//
//  public <Y> IList<Y> map(IFunc<T, Y> fun) {
//    return new ConsList<Y>(fun.apply(this.first), this.rest.map(fun));
//  }
//
//  public <U> U fold(IFunc2<T, U, U> fun, U base) {
//    return fun.apply(this.first, this.rest.fold(fun, base));
//  }
//
//  public boolean ormap(IPred<T> pred) {
//    return pred.apply(this.first) || this.rest.ormap(pred);
//  }
//
//  public boolean andmap(IPred<T> pred) {
//    return pred.apply(this.first) && this.rest.andmap(pred);
//  }
//}
//
//class greaterThan implements IPred<Integer> {
//  Integer i;
//  
//  greaterThan(Integer i) {
//    this.i = i;
//  }
//  
//  public boolean apply(Integer x) {
//    return x > i;
//  }
//}
//
///*
//class pointsInList implements IPred<CartPt> {
//  IList<CartPt> points;
//  
//  pointsInList(IList<CartPt> points) {
//    this.points = points;
//  }
//  
//  //returns true if the given CartPt is contained within points
//  public boolean apply(CartPt x) {
//    return 
//  }
//}
//*/
//
//class drawList implements IFunc2<IGamePiece, WorldScene, WorldScene> {
//
//  @Override
//  public WorldScene apply(IGamePiece x, WorldScene y) {
//    return x.renderImage(y);
//  }
//}
//
//class Utils<X> {
//  IList<X> buildList(int max, IFunc3<X> func) {
//    return this.buildListHelp(max, func, 0);
//  }
//  IList<X> buildListHelp(int max, IFunc3<X> func, int current) {
//    if (current >= max) {
//      return new MtList<X>();
//    }
//    return new ConsList<X>(func.apply(current), this.buildListHelp(max, func, current + 1));
//  }
//}
//
//class addtwo implements IFunc3<Integer> {
//  
//  public Integer apply(int x) {
//    return x + 2;
//  }
//}
//class divisableByTwo implements IFunc3<Boolean> {
//  public Boolean apply(int x) {
//    return x % 2 == 0;
//  }
//}
//
//
//
