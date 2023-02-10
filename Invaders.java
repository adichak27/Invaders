import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;


class GameRunner extends World {

  Random r;
  
  boolean gameOver = false;
  Spaceship ship;
  
  WorldScene finalScene = this.getEmptyScene().placeImageXY(new TextImage("You Lost", Color.red), 250, 250);
  
  WorldScene winningScene = this.getEmptyScene().placeImageXY(new TextImage("Congrats You Won!", Color.blue), 250, 250);

  
  IList<AGamePiece> invaders;
  IList<AGamePiece> sbullets;
  IList<AGamePiece> ibullets;

  GameRunner(Spaceship ship, IList<AGamePiece> invaders, IList<AGamePiece> sbullets,
      IList<AGamePiece> ibullets, Random r) {
    this.ship = ship;
    this.invaders = invaders;
    this.sbullets = sbullets;
    this.ibullets = ibullets;
    this.r = r;
  }

  // returns a worldscene of the spaceshiip, bullets, and invaders
  public WorldScene makeScene() {
    return this.ship.renderImage(this.ibullets.fold(new DrawList(), this.sbullets
        .fold(new DrawList(), this.invaders.fold(new DrawList(), this.getEmptyScene()))));
  }
  public GameRunner onTick() {

   
    this.ship.movePiece();
    this.sbullets.map(new MoveBullets());
    this.ibullets.map(new MoveBullets());
    
    this.sbullets = this.sbullets.filter(new OnScreen());
    
    this.ibullets = this.ibullets.filter(new OnScreen());
    
    IList<AGamePiece> tempInvaders = this.invaders;
    
    this.invaders = this.invaders.filter(new PointsNotInList(this.sbullets, 10, 10));
    
    this.sbullets = this.sbullets.filter(new PointsNotInList(tempInvaders, 10, 10));
    
    this.ibullets = this.invaders.fold(new FireInvaders(this.r), this.ibullets);
    
    gameOver = this.ibullets.ormap(new SamePt(this.ship, 10, 10)) || this.invaders.length() == 0;
    
    return this;
    
    
  }
  public GameRunner onKeyReleased(String str) {
    if (str.equals(" ") && sbullets.length() < 3) {
      this.sbullets = new ConsList<AGamePiece>(this.ship.fireBullet(), this.sbullets);
    }
    if (str.equals("left")) {
      this.ship.changeDirection(false);
    }
    if (str.equals("right")) {
      this.ship.changeDirection(true);
    }
    return this;
    
  }
  public WorldEnd worldEnds() {
    if (gameOver) {
      if(this.invaders.length() > 0) {
        return new WorldEnd(true, this.finalScene);

      } else {
        return new WorldEnd(true, this.winningScene);
      }

    } else {
      return new WorldEnd(false, this.makeScene());
    }
  } 
}


// represents a game piece
interface IGamePiece {
  // render this piece onto the given screen
  WorldScene renderImage(WorldScene base);
  
  void movePiece();
  
  AGamePiece fireBullet();
  
  void changeDirection(boolean direction);
  
  boolean onScreen();
  
  boolean collision(AGamePiece other, int xTolerance, int yTolerance);

  
}

abstract class AGamePiece implements IGamePiece {
  CartPt loc;
  WorldImage image;

  AGamePiece(CartPt loc, WorldImage image) {
    this.loc = loc;
    this.image = image;
  }

  /*
   * fields: this.loc ... CartPt this.image ... WorldImage
   * 
   * methods: this.renderImage(WorldScene) ... WorldScene
   * 
   */
  

  // render this piece onto the given screen
  public WorldScene renderImage(WorldScene base) {
    return base.placeImageXY(this.image, this.loc.x, this.loc.y);
  }

// returns true if this AGamePiece is within the bounds of the screen
  public boolean onScreen() {
    return this.loc.x < 500 && this.loc.x > 0 && this.loc.y <500 && this.loc.y > 0;
  }
  
  // checks if two game pieces have come within the given x and y values of each other
  public boolean collision(AGamePiece other, int xTolerance, int yTolerance) {
    return this.loc.x - other.loc.x <= xTolerance && this.loc.y - other.loc.y <= yTolerance
        && other.loc.x - this.loc.x <= xTolerance && other.loc.y - this.loc.y <= yTolerance;
  }
  
  // only used to test movePiece method
  public CartPt getLoc() {
    return this.loc;
  }
}

// represents an invader
class Invader extends AGamePiece {

  Invader(CartPt loc) {
    super(loc, new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED));
  }

  @Override
  public void movePiece() {
    
  }

  @Override
  public AGamePiece fireBullet() {
    return new InvaderBullet(new CartPt(this.loc.x, this.loc.y));
  }

  @Override
  public void changeDirection(boolean direction) {
  }

 
}



// represents a spaceShip
class Spaceship extends AGamePiece {
  int speed = 10;

  // true indicate moving right, false indicates moving left. default is true
  boolean direction;

  Spaceship(CartPt loc) {
    super(loc, new RectangleImage(40, 20, OutlineMode.SOLID, Color.BLACK));
    this.direction = true;
  }
 
  
  public void movePiece() {
    if (this.direction && this.loc.x < 480) {
      this.loc.x = this.loc.x + this.speed;
    } else if (this.loc.x > 20 && !this.direction){
      this.loc.x = this.loc.x - this.speed;
    } 
  }
  public AGamePiece fireBullet() {
    return new SpaceshipBullet(new CartPt(this.loc.x, this.loc.y));
  }

  // changes the direction of Spaceship
  public void changeDirection(boolean newDirection) {
    this.direction = newDirection;
  }
  
  // only used to test void changeDirection
  public boolean getDirection() {
    return this.direction; 
  }
}

// represents an invader bullet
class InvaderBullet extends AGamePiece {
  int speed = 10;
  
  InvaderBullet(CartPt loc) {
    super(loc, new CircleImage(5, OutlineMode.SOLID, Color.RED));
  }

  @Override
  public void movePiece() {
    this.loc.y = this.loc.y + this.speed;
    
  }

  @Override
  public AGamePiece fireBullet() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void changeDirection(boolean direction) {
    
  }
}

// represents a spaceship bullet
class SpaceshipBullet extends AGamePiece {
  int speed = 10;

  SpaceshipBullet(CartPt loc) {
    super(loc, new CircleImage(5, OutlineMode.SOLID, Color.BLACK));
  }

  
  public void movePiece() {
    this.loc.y = this.loc.y - this.speed;
  }


  @Override
  public AGamePiece fireBullet() {
    
    return null;
  }


  @Override
  public void changeDirection(boolean direction) {
    
  }
}

// represents the location on the cartesian plane
class CartPt {
  int x;
  int y;

  CartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /*
   * fields: this.x ... int this.y ... int
   * 
   * methods: this.samePoint(CartPt) ... boolean
   * 
   */

  // returns true if the given CartPt is the same as this one
  boolean samePoint(CartPt other, int xTolerance, int yTolerance) {
    return this.x - other.x <= xTolerance && this.y - other.y <= yTolerance
        && other.x - this.x <= xTolerance && other.y - this.y <= yTolerance;
  }
  
}

class ExamplesGame {

  IList<Integer> mtInt = new MtList<Integer>();
  IList<Integer> buildI1 = new ConsList<Integer>(1, this.mtInt); // 1
  IList<Integer> buildI2 = new ConsList<Integer>(2, this.buildI1); // 2,1
  IList<Integer> buildI3 = new ConsList<Integer>(3, this.buildI2); // 3,2,1
  IList<Integer> buildI4 = new ConsList<Integer>(4, this.buildI3); // 4,3,2,1

  IList<String> mtString = new MtList<String>();
  IList<String> buildS1 = new ConsList<String>("michael", this.mtString); // michael
  IList<String> buildS2 = new ConsList<String>("john", this.buildS1); // john, michael
  IList<String> buildS3 = new ConsList<String>("harry", this.buildS2); // harry, john, michael
  IList<String> buildS4 = new ConsList<String>("drew", this.buildS3); // drew, harry, john, michael

  IList<Integer> buildI1match = new ConsList<Integer>(7, this.mtInt); // 7
  IList<Integer> buildI2match = new ConsList<Integer>(4, this.buildI1match); // 4,7
  IList<Integer> buildI3match = new ConsList<Integer>(5, this.buildI2match); // 5,4,7
  IList<Integer> buildI4match = new ConsList<Integer>(4, this.buildI3match); // 4,5,4,7

  IList<String> buildS1U = new ConsList<String>("MICHAEL", this.mtString); // MICHAEL
  IList<String> buildS2U = new ConsList<String>("JOHN", this.buildS1U); // JOHN, MICHAEL
  IList<String> buildS3U = new ConsList<String>("HARRY", this.buildS2U); // HARRY, JOHN, MICHAEL
  IList<String> buildS4U = new ConsList<String>("DREW", this.buildS3U); // DREW, HARRY, JOHN,
  // MICHAEL
  

  CartPt pt1 = new CartPt(0, 0);
  CartPt pt2 = new CartPt(100, 150);
  CartPt pt3 = new CartPt(59, 500);
  CartPt pt4 = new CartPt(330, 0);
  

  WorldScene worldScene1 = new WorldScene(0,0);

  DrawList drawList1 = new DrawList();

  greaterThan GT1 = new greaterThan(1);
  greaterThan GT4 = new greaterThan(4);
  
  

  Utils<Integer> u = new Utils<Integer>();
  Utils<Boolean> u2 = new Utils<Boolean>();
  Utils<AGamePiece> u3 = new Utils<AGamePiece>();


  IList<CartPt> mtPoints = new MtList<CartPt>();

  IList<CartPt> points1 = new ConsList<CartPt>(this.pt1, this.mtPoints);
  IList<CartPt> points2 = new ConsList<CartPt>(this.pt2, this.points1);
  IList<CartPt> points3 = new ConsList<CartPt>(this.pt3, this.points2);
  IList<CartPt> points4 = new ConsList<CartPt>(this.pt4, this.points3);

  Spaceship ship1 = new Spaceship(new CartPt(250, 500));
  Spaceship ship2 = new Spaceship(new CartPt(300, 550));
  Spaceship ship3 = new Spaceship(new CartPt(105, 355));

  AGamePiece invader1 = new Invader(new CartPt(50, 50));
  AGamePiece invader2 = new Invader(new CartPt(100, 50));
  AGamePiece invader3 = new Invader(new CartPt(150, 100));
  AGamePiece invader4 = new Invader(new CartPt(65, 305));
  AGamePiece invaderOffScreen = new Invader(new CartPt(1000, 50));
  AGamePiece invaderSamePt6 = new Invader(new CartPt(40, 60));

  


  AGamePiece shipBullet1 = new SpaceshipBullet(new CartPt(60, 300));
  AGamePiece shipBullet2 = new SpaceshipBullet(new CartPt(400, 200));
  AGamePiece shipBullet3 = new SpaceshipBullet(new CartPt(270, 100));
  AGamePiece shipBullet4 = new SpaceshipBullet(new CartPt(55, 55));
  AGamePiece shipBullet5 = new InvaderBullet(new CartPt(105, 360));
  AGamePiece shipBulletOffScreen = new InvaderBullet(new CartPt(20,700));
  AGamePiece sBulletSamePt7 = new InvaderBullet(new CartPt(70,300));

  


  AGamePiece invaderBullet1 = new InvaderBullet(new CartPt(100, 355));
  AGamePiece invaderBullet2 = new InvaderBullet(new CartPt(420, 75));
  AGamePiece invaderBullet3 = new InvaderBullet(new CartPt(330, 100));
  AGamePiece invaderBullet4 = new InvaderBullet(new CartPt(255, 505));
  AGamePiece invaderBullet5 = new InvaderBullet(new CartPt(65, 305));
  AGamePiece invaderBulletOffScreen = new InvaderBullet(new CartPt(600, 600));
  AGamePiece iBulletSamePt8 = new InvaderBullet(new CartPt(150, 100));


  IList<AGamePiece> mt = new MtList<AGamePiece>();
  
  SamePt pt5 = new SamePt(invader1, 10, 10);
  SamePt pt6 = new SamePt(invader1, 10, 10);
  SamePt pt7 = new SamePt(invader4, 20, 20);
  SamePt pt8 = new SamePt(invader3, 0, 0);
   
  
//  IList<IGamePiece> invaders = new ConsList<IGamePiece>(this.invader1,
//      new ConsList<IGamePiece>(this.invader2, new ConsList<IGamePiece>(this.invader3, this.mt)));
  
  IList<AGamePiece> sbullets = new ConsList<AGamePiece>(this.shipBullet1, new ConsList<AGamePiece>(
      this.shipBullet2, new ConsList<AGamePiece>(this.shipBullet3, this.mt)));
  IList<AGamePiece> ibullets = new ConsList<AGamePiece>(this.invaderBullet1,
      new ConsList<AGamePiece>(this.invaderBullet2,
          new ConsList<AGamePiece>(this.invaderBullet3, this.mt)));

  IList<AGamePiece> invaders = this.u3.buildList(36, new InitializeInvaders());
  
  GameRunner x = new GameRunner(this.ship1, this.invaders, this.sbullets, this.ibullets, new Random());

  void InitData() {
    this.ship1 = new Spaceship(new CartPt(250, 500));
    this.invader1 = new Invader(new CartPt(50,50));
    this.shipBullet1 = new SpaceshipBullet(new CartPt(60, 300));
    this.shipBullet2 = new SpaceshipBullet(new CartPt(400, 200));
    this.shipBullet3 = new SpaceshipBullet(new CartPt(270, 100));
    this.shipBullet4 = new SpaceshipBullet(new CartPt(55, 55));
    this.invaderBullet1 = new InvaderBullet(new CartPt(100, 355));
    this.invaderBullet2 = new InvaderBullet(new CartPt(420, 75));
    this.invaderBullet3 = new InvaderBullet(new CartPt(330, 100));
    this.invaderBullet4 = new InvaderBullet(new CartPt(255, 505));
    this.mt = new MtList<AGamePiece>();
    this.sbullets = new ConsList<AGamePiece>(this.shipBullet1, new ConsList<AGamePiece>(
      this.shipBullet2, new ConsList<AGamePiece>(this.shipBullet3, this.mt)));

    
    this.ibullets = new ConsList<AGamePiece>(this.invaderBullet1,
      new ConsList<AGamePiece>(this.invaderBullet2,
          new ConsList<AGamePiece>(this.invaderBullet3, this.mt)));

  this.invaders = this.u3.buildList(36, new InitializeInvaders());
  }
  // test for filter
  boolean testFilter(Tester t) {
    return t.checkExpect(this.mtInt.filter(new AboveValueInt(4)), this.mtInt)
        && t.checkExpect(this.buildI4.filter(new AboveValueInt(3)),
            new ConsList<Integer>(4, this.mtInt))
        && t.checkExpect(this.buildS4.filter(new AboveValueString(6)), this.buildS1);
  }

  // test for ormap
  boolean testOrMap(Tester t) {
    return t.checkExpect(this.mtInt.ormap(new AboveValueInt(4)), false)
        && t.checkExpect(this.buildI4.ormap(new AboveValueInt(3)), true)
        && t.checkExpect(this.buildI4.ormap(new AboveValueInt(5)), false)
        && t.checkExpect(this.buildS4.ormap(new AboveValueString(7)), false)
        && t.checkExpect(this.buildS4.ormap(new AboveValueString(6)), true);
  }


  // test for andmap
  boolean testAndMap(Tester t) {
    return t.checkExpect(this.mtInt.andmap(new AboveValueInt(4)), true)
        && t.checkExpect(this.buildI4.andmap(new AboveValueInt(0)), true)
        && t.checkExpect(this.buildI4.andmap(new AboveValueInt(2)), false)
        && t.checkExpect(this.buildS4.andmap(new AboveValueString(1)), true)
        && t.checkExpect(this.buildS4.andmap(new AboveValueString(5)), false);
  }

  // test for map
  boolean testMap(Tester t) {
    return t.checkExpect(this.mtString.map(new StringToUpper()), this.mtString)
        && t.checkExpect(this.buildS4.map(new StringLength()), this.buildI4match)
        && t.checkExpect(this.buildS4.map(new StringToUpper()), this.buildS4U);
  }

  // test for fold
  boolean testFold(Tester t) {
    return t.checkExpect(this.mtString.fold(new StringLengthSum(), 0), 0)
        && t.checkExpect(this.buildS4.fold(new StringLengthSum(), 0), 20)
        && t.checkExpect(this.buildI4.fold(new IntegerSum(), 0), 10);
  }

  // test for renderImage
  boolean testRenderImage(Tester t) {
    return t.checkExpect(this.invader1.renderImage(this.x.getEmptyScene()),
        this.x.getEmptyScene()
        .placeImageXY(new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED), 50, 50))
        && t.checkExpect(this.ship1.renderImage(this.x.getEmptyScene()),
            this.x.getEmptyScene()
            .placeImageXY(new RectangleImage(40, 20, OutlineMode.SOLID, Color.BLACK), 250, 500))
        && t.checkExpect(this.shipBullet1.renderImage(this.x.getEmptyScene()),
            this.x.getEmptyScene().placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.BLACK),
                60, 300))
        && t.checkExpect(this.invaderBullet1.renderImage(this.x.getEmptyScene()),
            this.x.getEmptyScene().placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.RED),
                100, 355));
  }

  // test for PointsInList predicate
//  boolean testPointsInList(Tester t) {
//    return t.checkExpect(new PointsInList(this.mtPoints).apply(this.pt1), false)
//        && t.checkExpect(new PointsInList(this.points4).apply(this.pt3), true)
//        && t.checkExpect(new PointsInList(this.points4).apply(new CartPt(50, 50)), false);
//  }

  // test for AboveValueInt
  boolean testAboveValueInt(Tester t) {
    return t.checkExpect(new AboveValueInt(5).apply(0), false)
        && t.checkExpect(new AboveValueInt(5).apply(5), false)
        && t.checkExpect(new AboveValueInt(5).apply(10), true);
  }

  // test for AboveValueString
  boolean testAboveValueString(Tester t) {
    return t.checkExpect(new AboveValueString(5).apply(""), false)
        && t.checkExpect(new AboveValueString(5).apply("hi"), false)
        && t.checkExpect(new AboveValueString(5).apply("Strings"), true);
  }

  // test for AddValueInt
  boolean testAddValueInt(Tester t) {
    return t.checkExpect(new AddValueInt(0).apply(""), 0)
        && t.checkExpect(new AddValueInt(0).apply("hi"), 2)
        && t.checkExpect(new AddValueInt(5).apply("Strings"), 12);
  }

  // test for StringToUpper
  boolean testStringToUpper(Tester t) {
    return t.checkExpect(new StringToUpper().apply(""), "")
        && t.checkExpect(new StringToUpper().apply("hello"), "HELLO")
        && t.checkExpect(new StringToUpper().apply("hElLo"), "HELLO")
        && t.checkExpect(new StringToUpper().apply("HELLO"), "HELLO");
  }

  // test for StringLength
  boolean testStringLength(Tester t) {
    return t.checkExpect(new StringLength().apply(""), 0)
        && t.checkExpect(new StringLength().apply("hi"), 2)
        && t.checkExpect(new StringLength().apply("Strings"), 7);
  }

  // test for StringLengthSum
  boolean testStringLengthSum(Tester t) {
    return t.checkExpect(new StringLengthSum().apply("", 0), 0)
        && t.checkExpect(new StringLengthSum().apply("hi", 5), 7)
        && t.checkExpect(new StringLengthSum().apply("Strings", 1), 8);
  }

  // test for IntegerSum
  boolean testIntegerSum(Tester t) {
    return t.checkExpect(new IntegerSum().apply(0, 0), 0)
        && t.checkExpect(new IntegerSum().apply(2, 5), 7)
        && t.checkExpect(new IntegerSum().apply(7, 1), 8);
  }
  // test for length function
  boolean testlength(Tester t) {
    return t.checkExpect(this.mtInt.length(), 0) &&
        t.checkExpect(this.buildI4.length(), 4);
  }
  
  // tests divisableByTwo function
  boolean testdivisableByTwo(Tester t) {
    return t.checkExpect(new divisableByTwo().apply(2), true) &&
        t.checkExpect(new divisableByTwo().apply(3), false);
  }
  
  //tests addtwo function
 boolean testaddtwo(Tester t) {
   return t.checkExpect(new addtwo().apply(2), 4) &&
       t.checkExpect(new addtwo().apply(3), 5);
 }
  
  
  // test for the method samePt
  boolean testSamePt(Tester t) {
    return t.checkExpect(this.pt5.apply(invader1), true) &&
        t.checkExpect(this.pt6.apply(shipBullet1), false) &&
        t.checkExpect(this.pt7.apply(invaderBullet1), false) &&
        t.checkExpect(this.pt8.apply(ship1), false) && 
        t.checkExpect(this.pt8.apply(iBulletSamePt8), true) &&
        t.checkExpect(this.pt7.apply(sBulletSamePt7), true) && 
        t.checkExpect(this.pt6.apply(invaderSamePt6), true);
  }
  // test for the method onScreen()
  boolean testOnScreen(Tester t) {
    return t.checkExpect(this.invaderOffScreen.onScreen(), false) && 
        t.checkExpect(this.invaderBulletOffScreen.onScreen(), false) && 
        t.checkExpect(this.shipBulletOffScreen.onScreen(), false) && 
        t.checkExpect(this.ship1.onScreen(), false) && 
        t.checkExpect(this.invader1.onScreen(), true) && 
        t.checkExpect(this.ship3.onScreen(), true) && 
        t.checkExpect(this.shipBullet1.onScreen(), true) && 
        t.checkExpect(this.invaderBullet1.onScreen(), true); 
  }
  // tests for the method change direction 
  void testDirection(Tester t) {
    this.InitData();
     t.checkExpect(this.ship1.getDirection(), true);
     
     this.ship1.changeDirection(false);
     t.checkExpect(this.ship1.getDirection(), false);
     this.ship1.changeDirection(true);
     t.checkExpect(this.ship1.getDirection(), true);
  }
  
   
  

  // tests the apply function in the greaterThan class and drawList class
  boolean testApply(Tester t) { 
    return t.checkExpect(this.GT1.apply(2), true) &&
        t.checkExpect(this.GT4.apply(2), false) &&
        t.checkExpect(this.GT4.apply(4), false) &&
        t.checkExpect(this.GT1.apply(-2), false) &&
        t.checkExpect(this.drawList1.apply(invader1, this.x.getEmptyScene()), 
            this.x.getEmptyScene().placeImageXY(new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED), 50, 50)) &&
        t.checkExpect(this.drawList1.apply(ship1, this.x.getEmptyScene()), 
            this.x.getEmptyScene().placeImageXY(
                new RectangleImage(40, 20, OutlineMode.SOLID, Color.BLACK), 250, 500));
  }

  // test buildList method
  boolean testBuildList(Tester t) {
    return t.checkExpect(this.u.buildListHelp(5, new addtwo(), 3),
        new ConsList<Integer>(5, new ConsList<Integer>(6, new MtList<Integer>()))) &&
        t.checkExpect(this.u.buildListHelp(5, new addtwo(), 5), new MtList<Integer>()) && 
        t.checkExpect(this.u.buildList(0, new addtwo()), new MtList<Integer>()) &&
        t.checkExpect(this.u.buildList(3, new addtwo()), new ConsList<Integer> (2,
            new ConsList<Integer>(3, new ConsList<Integer> (4, new MtList<Integer>())))) &&
        t.checkExpect(this.u2.buildListHelp(5, new divisableByTwo(), 3),
            new ConsList<Boolean>(false, new ConsList<Boolean>(true, new MtList<Boolean>()))) &&
        t.checkExpect(this.u2.buildList(3, new divisableByTwo()),
            new ConsList<Boolean>(true, new ConsList<Boolean>(false, new ConsList<Boolean>(true,
                new MtList<Boolean>()))));
  }

  // test for the game
  boolean testBigBang(Tester t) {
    return x.bigBang(500, 500, 0.05);
  }
  
  // tests InitializeInvaders function
  boolean testInitializeInvaders(Tester t) {
    return t.checkExpect(new InitializeInvaders().apply(5), new Invader(new CartPt(270, 40))) &&
        t.checkExpect(new InitializeInvaders().apply(10), new Invader(new CartPt(110, 80))) &&
        t.checkExpect(new InitializeInvaders().apply(20), new Invader(new CartPt(150, 120))) &&
        t.checkExpect(new InitializeInvaders().apply(30), new Invader(new CartPt(190, 160)));
  }
  
  
  // tests the MoveBullets() class 
  boolean testmoveBullets(Tester t) {
    this.InitData();
    return t.checkExpect(new MoveBullets().apply(this.invaderBullet1), new InvaderBullet(new CartPt(100, 365))) &&
        t.checkExpect(new MoveBullets().apply(this.shipBullet1), new SpaceshipBullet(new CartPt(60, 290)));
  }
  
////tests FireInvaders function
//boolean testFireInvaders(Tester t) {
//  this.InitData();
//  return t.checkExpect(new FireInvaders().apply(this.ship1, this.sbullets), this.sbulletadd) && 
//      t.checkExpect(new FireInvaders().apply(this.invader1, this.ibullets), this.ibulletadd);
//}

  // test Collision function
  boolean testCollision(Tester t) {
    this.InitData();
    return t.checkExpect(this.invader1.collision(this.invaderBullet1, 10, 10), false) &&
        t.checkExpect(this.invader1.collision(this.ship1, 10, 10), false) &&
        t.checkExpect(this.invader1.collision(this.invader2, 10, 10), false) &&
        t.checkExpect(this.invader1.collision(this.shipBullet1, 10, 10), false) &&
        t.checkExpect(this.invader1.collision(this.shipBullet4, 10, 10), true) &&
        t.checkExpect(this.ship1.collision(this.shipBullet1, 10, 10), false) &&
        t.checkExpect(this.ship1.collision(this.ship2, 10, 10), false) &&
        t.checkExpect(this.ship1.collision(this.invader1, 10, 10), false) &&
        t.checkExpect(this.ship1.collision(this.invaderBullet1, 10, 10), false) &&
        t.checkExpect(this.ship1.collision(this.invaderBullet4, 10, 10), true) &&     
        t.checkExpect(this.shipBullet1.collision(this.shipBullet2, 10, 10), false) &&
        t.checkExpect(this.shipBullet1.collision(this.ship2, 10, 10), false) &&
        t.checkExpect(this.shipBullet1.collision(this.invader2, 10, 10), false) &&
        t.checkExpect(this.shipBullet1.collision(this.invader4, 10, 10), true) &&
        t.checkExpect(this.shipBullet1.collision(this.invaderBullet1, 10, 10), false) &&
        t.checkExpect(this.shipBullet1.collision(this.invaderBullet5, 10, 10), true) &&
        t.checkExpect(this.invaderBullet1.collision(this.shipBullet5, 10, 10), true) &&
        t.checkExpect(this.invaderBullet1.collision(this.shipBullet1, 10, 10), false) &&
        t.checkExpect(this.invaderBullet1.collision(this.ship1, 10, 10), false) &&
        t.checkExpect(this.invaderBullet1.collision(this.ship3, 10, 10), true) &&
        t.checkExpect(this.invaderBullet1.collision(this.invader4, 10, 10), false) &&
        t.checkExpect(this.invaderBullet1.collision(this.invaderBullet2, 10, 10), false);
  }
//  test movePiece method
  void testMovePiece(Tester t) {
    this.InitData();
    
    t.checkExpect(this.ship1.getLoc(), new CartPt(250,500));
    t.checkExpect(this.invader1.getLoc(), new CartPt(50,50));
    t.checkExpect(this.invaderBullet1.getLoc(), new CartPt(100,355));
    t.checkExpect(this.shipBullet1.getLoc(), new CartPt(60,300));
    
    this.ship1.movePiece();
    this.invader1.movePiece();
    this.invaderBullet1.movePiece();
    this.shipBullet1.movePiece();
    
    t.checkExpect(this.ship1.getLoc(), new CartPt(260,500));
    t.checkExpect(this.invader1.getLoc(), new CartPt(50,50));
    t.checkExpect(this.invaderBullet1.getLoc(), new CartPt(100,365));
    t.checkExpect(this.shipBullet1.getLoc(), new CartPt(60,290));
    
  }
}

