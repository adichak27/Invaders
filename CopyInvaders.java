//import tester.Tester;
//import javalib.funworld.*;
//import javalib.worldimages.*;
//import java.awt.Color;
//import java.awt.color.*;
//
//class GameRunner extends World {
//  
//  IGamePiece ship;
//  IList<IGamePiece> invaders;
//  IList<IGamePiece> sbullets;
//  IList<IGamePiece> ibullets;
//
//  GameRunner(IGamePiece ship, IList<IGamePiece> invaders, IList<IGamePiece> sbullets, IList<IGamePiece> ibullets) {
//    this.ship = ship;
//    this.invaders = invaders;
//    this.sbullets = sbullets;
//    this.ibullets = ibullets;
//  }
//
//  public WorldScene makeScene() {
////    return this.getEmptyScene().placeImageXY(
//  //      shipImage, this.ship.loc.x,
//    //    this.ship.loc.y);
//    return this.ship.renderImage(this.ibullets.fold(new drawList(), this.sbullets.fold(new drawList(), this.invaders.fold(new drawList(), this.getEmptyScene()))));
//  }
//
//}
//
//class ExamplesGame {
//
//  IList<Integer> mtInt = new MtList<Integer>();
//
//  IList<Integer> list1 = new ConsList<Integer>(1, new ConsList<Integer>(2,
//      new ConsList<Integer>(3, new ConsList<Integer>(4, new ConsList<Integer>(5, this.mtInt)))));
//
//  IGamePiece ship1 = new Spaceship(new CartPt(250, 500));
//  
//  IGamePiece invader1 = new Invader(new CartPt(50, 50));
//  IGamePiece invader2 = new Invader(new CartPt(100, 50));
//  IGamePiece invader3 = new Invader(new CartPt(150, 100));
//  
//  IGamePiece shipBullet1 = new SpaceshipBullet(new CartPt(60, 300));
//  IGamePiece shipBullet2 = new SpaceshipBullet(new CartPt(400, 200));
//  IGamePiece shipBullet3 = new SpaceshipBullet(new CartPt(270, 100));
//  
//  IGamePiece invaderBullet1 = new InvaderBullet(new CartPt(100, 355));
//  IGamePiece invaderBullet2 = new InvaderBullet(new CartPt(420, 75));
//  IGamePiece invaderBullet3 = new InvaderBullet(new CartPt(330, 100));
//  
//  
//  IList<IGamePiece> mt = new MtList<IGamePiece>();
//  IList<IGamePiece> invaders = new ConsList<IGamePiece>(this.invader1, new ConsList<IGamePiece>(this.invader2, new ConsList<IGamePiece>(this.invader3, this.mt)));
//  IList<IGamePiece> sbullets = new ConsList<IGamePiece>(this.shipBullet1, new ConsList<IGamePiece>(this.shipBullet2, new ConsList<IGamePiece>(this.shipBullet3, this.mt)));
//  IList<IGamePiece> ibullets = new ConsList<IGamePiece>(this.invaderBullet1, new ConsList<IGamePiece>(this.invaderBullet2, new ConsList<IGamePiece>(this.invaderBullet3, this.mt)));
//
//  CartPt pt1 = new CartPt(50, 100);
//  CartPt pt2 = new CartPt(0, 0);
//  CartPt pt3 = new CartPt(-70, -100);
//  CartPt pt4 = new CartPt(50, 100);
//  
//  WorldScene worldScene1 = new WorldScene(0,0);
//  
//  drawList drawList1 = new drawList();
//  
//  greaterThan GT1 = new greaterThan(1);
//  greaterThan GT4 = new greaterThan(4);
//  
//  
//  
//  Utils<Integer> u = new Utils<Integer>();
//  Utils<Boolean> u2 = new Utils<Boolean>();
//  
//  GameRunner x = new GameRunner(this.ship1, this.invaders, this.sbullets, this.ibullets);
//
//  boolean testAbstractions(Tester t) {
//    return t.checkExpect(this.mtInt.filter(new greaterThan(2)), this.mtInt) && t.checkExpect(
//        this.list1.filter(new greaterThan(2)),
//        new ConsList<Integer>(3, new ConsList<Integer>(4, new ConsList<Integer>(5, this.mtInt))));
//  }
//  
//  boolean testRenderImage(Tester t) {
//    return t.checkExpect(this.invader1.renderImage(this.x.getEmptyScene()), this.x.getEmptyScene().placeImageXY(new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED), 50, 50));
//  }
//
//  
////  boolean testBigBang(Tester t) {
////    return x.bigBang(500, 500, 0.25);
////  }
//  boolean testSamePoint(Tester t) {
//    return t.checkExpect(this.pt1.samePoint(pt1), true) &&
//        t.checkExpect(this.pt1.samePoint(pt2), false) &&
//        t.checkExpect(this.pt1.samePoint(pt3), false) &&
//        t.checkExpect(this.pt1.samePoint(pt4), true);
//  }
//  // tests the apply function in the greaterThan class and 
//  boolean testApply(Tester t) { 
//    return t.checkExpect(this.GT1.apply(2), true) &&
//        t.checkExpect(this.GT4.apply(2), false) &&
//        t.checkExpect(this.GT4.apply(4), false) &&
//        t.checkExpect(this.GT1.apply(-2), false) &&
//        t.checkExpect(this.drawList1.apply(invader1, this.x.getEmptyScene()), 
//            this.x.getEmptyScene().placeImageXY(new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED), 50, 50));
//  }
//  
//  
//  boolean testBuildList(Tester t) {
//    return t.checkExpect(this.u.buildListHelp(5, new addtwo(), 3),
//        new ConsList<Integer>(5, new ConsList<Integer>(6, new MtList<Integer>()))) &&
//        t.checkExpect(this.u.buildListHelp(5, new addtwo(), 5), new MtList<Integer>()) && 
//        t.checkExpect(this.u.buildList(0, new addtwo()), new MtList<Integer>()) &&
//        t.checkExpect(this.u.buildList(3, new addtwo()), new ConsList<Integer> (2,
//            new ConsList<Integer>(3, new ConsList<Integer> (4, new MtList<Integer>())))) &&
//        t.checkExpect(this.u2.buildListHelp(5, new divisableByTwo(), 3),
//            new ConsList<Boolean>(false, new ConsList<Boolean>(true, new MtList<Boolean>()))) &&
//        t.checkExpect(this.u2.buildList(3, new divisableByTwo()),
//            new ConsList<Boolean>(true, new ConsList<Boolean>(false, new ConsList<Boolean>(true,
//                new MtList<Boolean>()))));
//  }
//}
//
//interface IGamePiece {
//  WorldScene renderImage(WorldScene base);
//}
//
//abstract class AGamePiece implements IGamePiece {
//  CartPt loc;
//  WorldImage image;
//  
//  AGamePiece(CartPt loc, WorldImage image) {
//    this.loc = loc;
//    this.image = image;
//  }
//  
//  public WorldScene renderImage(WorldScene base) {
//    return base.placeImageXY(this.image, this.loc.x, this.loc.y);
//  }
//}
//
//class Invader extends AGamePiece {
//  
//  Invader(CartPt loc) {
//    super(loc, new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED));
//  }
//}
//
//class Spaceship extends AGamePiece {
//
//  Spaceship(CartPt loc) {
//    super(loc, new RectangleImage(40, 20, OutlineMode.SOLID, Color.BLACK));
//  }
//}
//
//class InvaderBullet extends AGamePiece {
//
//  InvaderBullet(CartPt loc) {
//    super(loc, new CircleImage(5, OutlineMode.SOLID, Color.RED));
//  }
//}
//
//class SpaceshipBullet extends AGamePiece {
//
//  SpaceshipBullet(CartPt loc) {
//    super(loc, new CircleImage(5, OutlineMode.SOLID, Color.BLACK));
//  }
//}
//
//class CartPt {
//  int x;
//  int y;
//
//  CartPt(int x, int y) {
//    this.x = x;
//    this.y = y;
//  }
//  
//  //returns true if the given CartPt is the same as this one
//  boolean samePoint(CartPt other) {
//    return this.x == other.x && this.y == other.y;
//  }
//}