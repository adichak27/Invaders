import tester.Tester;

//represents an ancestor tree
interface IAT { 
  // returns true if the this IAT contains the given name
  boolean containsName(String name);
  
//  returns true if anyone in this ancestor tree has the same name as one of their ancestors 
  boolean duplicateNames();
  
  // returns true if the given person is the same generation as this person
  boolean sameGen(Person a);
  
  // determines the invididuals generation number
  int genNumber();

}
//represents a leaf on an ancestor tree
class Unknown implements IAT {

  
  public boolean containsName(String name) {
    return false;
  }

  
  public boolean duplicateNames() {
    return false;
  }


  @Override
  public boolean sameGen(Person a) {
    return false;
  }


  @Override
  public int genNumber() {
    return 0;
  } 
}
//represents a person in an ancestor tree
class Person implements IAT { 
  String name;
  IAT dad; 
  IAT mom;
  Person(String name, IAT dad, IAT mom) {
    this.name = name; 
    this.dad = dad; 
    this.mom = mom;
    
  }
  public int getMax(int a, int b) {
    if (a > b) {
      return a;
    } else {
      return b;
    }
  }
  
  public boolean containsName(String name) {
    return this.name.equals(name) || this.mom.containsName(name) || this.dad.containsName(name);
  }

//returns true if anyone in this ancestor tree has the same name as one of their ancestors 
  public boolean duplicateNames() {
    return this.mom.containsName(this.name) || this.dad.containsName(this.name) || 
        this.mom.duplicateNames() || this.dad.duplicateNames();
  }

  @Override
  public boolean sameGen(Person a) {
    return this.genNumber() == a.genNumber();
  }

  @Override
  public int genNumber() {
    return 1 + getMax(this.mom.genNumber(), this.dad.genNumber());
  } 
  
}
class ExamplesIAT {
  ILoString mtStrings = new MtLoString();
  ILoString list1 = new ConsLoString("A", new ConsLoString("B", 
      this.mtStrings));
  ILoString list2 = new ConsLoString("D", new ConsLoString("E", 
      this.mtStrings));
  ILoString list3 = new ConsLoString("A", new ConsLoString("D", new 
      ConsLoString("B", 
          new ConsLoString("E", 
              this.mtStrings))));
  
  String s1 = "Adi";
  String s2 = "Adi";
  IAT unknown = new Unknown();
  IAT davisSr = new Person("Davis", this.unknown, this.unknown); 
  IAT edna = new Person("Edna", this.unknown, this.unknown);
  IAT davisJr = new Person("Davis", this.davisSr, this.edna);
  IAT carl = new Person("Carl", this.unknown, this.unknown);
  IAT candace = new Person("Candace", this.davisJr, this.unknown);
  IAT claire = new Person("Claire", this.unknown, this.unknown);
  IAT bill = new Person("Bill", this.carl, this.candace);
  IAT bree = new Person("Bree", this.unknown, this.claire);
  IAT anthony = new Person("Anthony", this.bill, this.bree);
  
  boolean testBuildList(Tester t) {
    return t.checkExpect(this.s1.equals(s2), true) &&
        t.checkExpect(this.bree.containsName("Claire"), true) && 
        t.checkExpect(this.anthony.containsName("Anthony"), true) && 
        t.checkExpect(this.bree.containsName("Bill"), false) && 
        t.checkExpect(this.edna.duplicateNames(), false) && 
        t.checkExpect(this.davisJr.duplicateNames(), true) && 
        t.checkExpect(this.davisSr.duplicateNames(), false) && 
        t.checkExpect(this.anthony.duplicateNames(), true) && 
        t.checkExpect(this.list3.unzip(), new PairOfLists(list1, list2));
  }
}