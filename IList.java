import java.util.Random;
import javalib.funworld.*;

interface IPred<X> {
  // applies a predicate to x to produce a boolean
  boolean apply(X x);
}

interface IFunc<X, Y> {
  // applies a function to x to produce a y
  Y apply(X x);
}

interface IFunc2<X, Y, Z> {
  // applies a function to x and y to produce a z
  Z apply(X x, Y y);
}

interface IFunc3<X> {
  // applies a function to an integer to produce an x
  X apply(int i);
}

interface IList<T> {
  // filter this list by the given predicate
  IList<T> filter(IPred<T> pred);

  // returns true if at least one member of this list passes the predicate
  boolean ormap(IPred<T> pred);

  // returns true if all members of this list pass the predicate
  boolean andmap(IPred<T> pred);

  // maps a function onto every member of this list
  <Y> IList<Y> map(IFunc<T, Y> fun);

  // combines the items in this list using the given function
  <Y> Y fold(IFunc2<T, Y, Y> fun, Y base);
  
  //returns number of items in this list
  int length();
  
  
}

class MtList<T> implements IList<T> {

  /*
   * fields: none
   * 
   * methods: this.filter(IPred<T>) ... IList<T> this.ormap(IPred<T>) ... boolean
   * this.andmap(IPred<T>) ... boolean this.map(IFunc<T, Y>) ... IList<Y>
   * this.fold(IFunc2<T, Y, Y>, Y) ... Y
   */

  // filter this list by the given predicate
  public IList<T> filter(IPred<T> pred) {
    /*
     * fields: pred ... IPred<T>
     * 
     * methods for fields: pred.apply(T) ... boolean
     */
    return this;
  }

  // returns true if at least one member of this list passes the predicate
  public boolean ormap(IPred<T> pred) {
    /*
     * fields: pred ... IPred<T>
     * 
     * methods for fields: pred.apply(T) ... boolean
     */

    return false;
  }

  // returns true if all members of this list pass the predicate
  public boolean andmap(IPred<T> pred) {
    /*
     * fields: pred ... IPred<T>
     * 
     * methods for fields: pred.apply(T) ... boolean
     */

    return true;
  }

  // maps a function onto every member of this list
  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    /*
     * fields: fun ... IFunc<T, Y>
     * 
     * methods for fields: fun.apply(T) ... Y
     */

    return new MtList<Y>();
  }

  // combines the items in this list using the given function
  public <U> U fold(IFunc2<T, U, U> fun, U base) {
    /*
     * fields: fun ... IFunc2<T, U, U> base ... U
     * 
     * methods for fields: fun.apply(T, U) ... U
     */

    return base;
  }
//returns number of items in this list
  public int length() {
    return 0;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * fields: this.first ... T this.rest ... IList<T>
   * 
   * methods: this.filter(IPred<T>) ... IList<T> this.ormap(IPred<T>) ... boolean
   * this.andmap(IPred<T>) ... boolean this.map(IFunc<T, Y>) ... IList<Y>
   * this.fold(IFunc2<T, Y, Y>, Y) ... Y
   * 
   * methods for fields: this.rest.filter(IPred<T>) ... IList<T>
   * this.rest.ormap(IPred<T>) ... boolean this.rest.andmap(IPred<T>) ... boolean
   * this.rest.map(IFunc<T, Y>) ... IList<Y> this.rest.fold(IFunc2<T, Y, Y>, Y)
   * ... Y
   */

  // filter this list by the given predicate
  public IList<T> filter(IPred<T> pred) {
    /*
     * fields: same as class pred ... IPred<T>
     * 
     * methods for fields: pred.apply(T) ... boolean
     */

    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  // returns true if at least one member of this list passes the predicate
  public boolean ormap(IPred<T> pred) {
    /*
     * fields: same as class pred ... IPred<T>
     * 
     * methods for fields: pred.apply(T) ... boolean
     */

    return pred.apply(this.first) || this.rest.ormap(pred);
  }

  // returns true if all members of this list pass the predicate
  public boolean andmap(IPred<T> pred) {
    /*
     * fields: same as class pred ... IPred<T>
     * 
     * methods for fields: pred.apply(T) ... boolean
     */

    return pred.apply(this.first) && this.rest.andmap(pred);
  }

  // maps a function onto every member of this list
  public <Y> IList<Y> map(IFunc<T, Y> fun) {
    /*
     * fields: same as class fun ... IFunc<T, Y>
     * 
     * methods for fields: fun.apply(T) ... Y
     */

    return new ConsList<Y>(fun.apply(this.first), this.rest.map(fun));
  }

  // combines the items in this list using the given function
  public <U> U fold(IFunc2<T, U, U> fun, U base) {
    /*
     * fields: same as class fun ... IFunc2<T, U, U> base ... U
     * 
     * methods for fields: fun.apply(T, U) ... U
     */

    return fun.apply(this.first, this.rest.fold(fun, base));
  }

  //returns number of items in this list
  public int length() {
    return 1 + this.rest.length();
  }
}

class PointsNotInList implements IPred<AGamePiece> {
  IList<AGamePiece> points;
  int xTolerance; 
  int yTolerance;
  

  PointsNotInList(IList<AGamePiece> points, int xTolerance, int yTolerance) {
    this.points = points;
    this.xTolerance = xTolerance;
    this.yTolerance = yTolerance;
  }

  /*
   * fields: this.points ... IList<CartPt>
   * 
   * methods: this.apply(CartPt) ... boolean
   * 
   * methods for fields: this.points.filter(IPred<T>) ... IList<T>
   * this.points.ormap(IPred<T>) ... boolean this.points.andmap(IPred<T>) ...
   * boolean this.points.map(IFunc<T, Y>) ... IList<Y> this.points.fold(IFunc2<T,
   * Y, Y>, Y) ... Y
   * 
   */

  // returns true if the given CartPt is contained within points
  public boolean apply(AGamePiece x) {
    /*
     * fields: this.points ... IList<CartPt>
     * 
     * methods: this.apply(CartPt) ... boolean
     */

    // this equation returns the points in list. We notted it so it returns points not in list. 
    return !this.points.ormap(new SamePt(x, xTolerance, yTolerance));
  }
}

//
class SamePt implements IPred<AGamePiece> {
  AGamePiece piece;
  int xTolerance; 
  int yTolerance;

  SamePt(AGamePiece piece, int xTolerance, int yTolerance) {
    this.piece = piece;
    this.xTolerance = xTolerance;
    this.yTolerance = yTolerance;
  }

  /*
   * fields: this.x ... CartPoint
   * 
   * methods: this.apply(CartPt) ... boolean
   * 
   * methods for fields: this.x.samePt(CartPt) ... boolean
   * 
   */
  
  // checks if the given CartPt is the same as this.x
//  public boolean apply(CartPt cp) {
//    return this.loc.samePt(cp);
//  }
  
  public boolean apply(AGamePiece p) {
    return p.collision(this.piece, xTolerance, yTolerance);
       
  }
}

class OnScreen implements IPred<AGamePiece> {
  
  public boolean apply(AGamePiece piece) {
    return piece.onScreen();
  }
}

class DrawList implements IFunc2<AGamePiece, WorldScene, WorldScene> {

  /*
   * fields: none
   * 
   * methods: this.apply(IGamePiece, WorldScene) ... WorldScene
   * 
   */

  // draws x onto y
  public WorldScene apply(AGamePiece x, WorldScene y) {
    return x.renderImage(y);
  }

}

class FireInvaders implements IFunc2<AGamePiece, IList<AGamePiece>, IList<AGamePiece>> {
  Random r;
  
  FireInvaders(Random r) {
    this.r = r;
  }
  
  public IList<AGamePiece> apply(AGamePiece x, IList<AGamePiece> y) {
    if (y.length() >= 10 || r.nextInt(300) > 1) {
      return y;
    } else {
      return new ConsList<AGamePiece>(x.fireBullet(), y);
    }
  }
  
}

// moves each of the bullets in a list by 10 on every tick
class MoveBullets implements IFunc<AGamePiece, AGamePiece>{
  public AGamePiece apply(AGamePiece bullet) {
    bullet.movePiece();
    return bullet;
  }
}

class InitializeInvaders implements IFunc3<AGamePiece>{

  public AGamePiece apply(int i) {
    if (i < 9) {
      return new Invader(new CartPt(i * 40 + 70, 40));
    } else if (i < 18) {
      return new Invader(new CartPt((i-9) * 40 + 70, 80));
      
    } else if (i < 27) {
      return new Invader(new CartPt((i-18) * 40 + 70, 120)); 
    } else {
      return new Invader(new CartPt((i-27) * 40 + 70, 160)); 
    }
  }
}
// Utils class 
class Utils<X> {

  /*
   * fields: none
   * 
   * methods: 
   * this.buildListHelp(int max, IFunc3<X> func) ... IList<X>
   * this.buildListHelp(int max, IFunc3<X> func, int current) ... IList<X>
   * 
   * 
   */
  // applies a function to a list of number from 0 to max and returns an IList<X>
  IList<X> buildList(int max, IFunc3<X> func) {
    return this.buildListHelp(max, func, 0);
  }

  // helps keep track of what iteration we are currently on and stops applying the given function when
  // current >= max
  IList<X> buildListHelp(int max, IFunc3<X> func, int current) {
    if (current >= max) {
      return new MtList<X>();
    }
    return new ConsList<X>(func.apply(current), this.buildListHelp(max, func, current + 1));
  }
}






class greaterThan implements IPred<Integer> {
  Integer i;

  greaterThan(Integer i) {
    this.i = i;
  }

  /*
   * fields: this.i ... Integer
   * 
   * methods: this.apply(Integer) ... boolean
   * 
   */

  public boolean apply(Integer x) {
    return x > i;
  }

}

// class to test apply function
class addtwo implements IFunc3<Integer> {
  /*
   * fields: none
   * 
   * methods: 
   * this.apply(int x) ... Integer
   * 
   */

  // addstwo to the given integer
  public Integer apply(int x) {
    return x + 2;
  }
}

// class to test apply function
class divisableByTwo implements IFunc3<Boolean> {

  /*
   * fields: none
   * 
   * methods: 
   * this.apply(int x) ... Boolean
   * 
   */

  // returns true if the given x is divisable by 2
  public Boolean apply(int x) {
    return x % 2 == 0;
  }
}


//classes to test filter, ormap, andmap, map, fold
class AboveValueInt implements IPred<Integer> {
  Integer val;

  AboveValueInt(Integer val) {
    this.val = val;
  }

  // is the given number above a number
  public boolean apply(Integer x) {
    return x > this.val;
  }
}

class AboveValueString implements IPred<String> {
  int val;

  AboveValueString(int val) {
    this.val = val;
  }

  //is the given word length above this number
  public boolean apply(String x) {
    return x.length() > this.val;
  }
}

class AddValueInt implements IFunc<String, Integer> {
  Integer val;

  AddValueInt(Integer val) {
    this.val = val;
  }

  // add a given number to this number
  public Integer apply(String x) {
    return x.length() + this.val;
  }
}

//changes string to uppercase
class StringToUpper implements IFunc<String, String> {

  public String apply(String x) {
    return x.toUpperCase();
  }
}

//changes string to their length
class StringLength implements IFunc<String, Integer> {

  /*
   * fields: none
   * 
   * methods: this.apply(String) ... Integer
   */

  public Integer apply(String x) {
    return x.length();
  }
}

//adds all the sum of the string lengths
class StringLengthSum implements IFunc2<String, Integer, Integer> {

  /*
   * fields: none
   * 
   * methods: this.apply(String, Integer) ... Integer
   * 
   */

  public Integer apply(String x, Integer i) {
    return i + x.length();
  }
}

//adds all the numbers in a list
class IntegerSum implements IFunc2<Integer, Integer, Integer> {

  /*
   * fields: none
   * 
   * methods for fields: this.apply(Integer, Integer) ... Integer
   * 
   */

  public Integer apply(Integer x, Integer i) {
    return i + x;
  }
}
