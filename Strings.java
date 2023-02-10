//import tester.Tester;
//
//interface ILoString {
//
//  // combine all Strings in the list into one
//  String combine();
//
//  // Sorts the list in alphabetical order
//  ILoString sort();
//
//  // Inserts a given string into an alphabetically sorted list of strings
//  ILoString insert(String s);
//
//  // produces a list where all occurrences of the first given String are replaced
//  // by the second given String
//  ILoString findAndReplace(String strOne, String strTwo);
//
//  // produces a boolean that determines if the list is sorted in alphabetical
//  // order and is not case sensitive
//  boolean isSorted();
//
//  // checks if the current string is sorted.
//  boolean isSortedHelper(String s);
//
//  // determines if the list has any duplicate strings
//  boolean anyDupes();
//
//  // takes in a string and checks if there are any other occurrences of the string
//  // in the list.
//  boolean anyDupesHelper(String s);
//
//  // produces a list where the first, third, fifth... elements are from this list
//  // and
//  // the second, fourth, sixth... elements are from the given list
//  ILoString interleave(ILoString givenList);
//
//  // takes this sorted list of string and a given sorted list of strings and
//  // produces a sorted
//  // list of strings that includes all items from lists including duplicates.
//  ILoString merge(ILoString givenSortedList);
//
//  // helps merge two lists. s is the string from this list and givenSortedList is
//  // the sorted list that is given
//  ILoString mergeHelper(String s, ILoString givenSortedList);
//
//  // determines if this list contains pairs of identical strings. The first and
//  // second strings are the same,
//  // the third and fourth are the same, the fifth and sixth are the same, etc.
//  boolean isDoubledList();
//
//  // helps check if the list is doubled. acc keeps track of the list so far
//  boolean isDoubledListAcc(String acc);
//
//  // returns this list of strings in reverse and combines them into one string
//  String reverseConcat();
//
//  // helps reverse the list. acc keeps track of the reverse list.
//  ILoString reverseAcc(ILoString acc);
//
//  // determines if this list contains the same words reading the list in either
//  // order
//  boolean isPalindromeList();
//
//}
//
//// represents a non empty list of strings
//class ConsLoString implements ILoString {
//  String first;
//  ILoString rest;
//
//  ConsLoString(String first, ILoString rest) {
//    this.first = first;
//    this.rest = rest;
//  }
//
//  /*
//   * TEMPLATE FIELDS: this.first ... String this.rest ... ILoString
//   * 
//   * METHODS this.comebine() ... String this.sort() ... ILoString
//   * this.insert(String s) ... ILoString this.findAndReplace(String strOne, String
//   * strTwo) ... ILoString this.isSorted() ... boolean this.isSortedHelper(String
//   * s) ... boolean this.interleave(ILoString givenList) ... ILoString
//   * this.merge(ILoString givenSortedList) ... ILoString this.mergeHelper(String
//   * s, ILoString givenSortedList) ... ILoString this.isDoubledList() ... boolean
//   * this.isDoubledListAcc(String acc) ... acc this.reverseConcat() ... String
//   * this.reverseAcc(ILoString acc) ... ILoString this.isPalindromeList()...
//   * boolean
//   * 
//   * METHODS FOR FIELDS this.rest.comebine() ... String this.rest.sort() ...
//   * ILoString this.rest.insert(String s) ... ILoString
//   * this.rest.findAndReplace(String strOne,String strTwo) ... ILoString
//   * this.rest.isSorted() ... boolean this.rest.isSortedHelper(String s) ...
//   * boolean this.rest.interleave(ILoString givenList) ... ILoString
//   * this.rest.merge(ILoString givenSortedList) ... ILoString
//   * this.rest.mergeHelper(String s, ILoString givenSortedList) ... ILoString
//   * this.rest.isDoubledList() ... boolean this.rest.isDoubledAcc(acc) ... boolean
//   * this.rest.reverseConcat() ... String this.rest.reverseAcc() ... ILoString
//   * this.rest.isPalindromeList() ... boolean
//   */
//
//  // combine all Strings in this list into one
//  public String combine() {
//    return this.first.concat(this.rest.combine());
//  }
//
//  // Sorts the ILoString in alphabetical order and is not case sensitive
//  public ILoString sort() {
//    return this.rest.sort().insert(this.first);
//  }
//
//  // inserts the given string into a list of strings alphabetically sorted
//  public ILoString insert(String s) {
//    if (this.first.toLowerCase().compareTo(s.toLowerCase()) <= 0) {
//      return new ConsLoString(this.first, this.rest.insert(s));
//    }
//    else {
//      return new ConsLoString(s, this);
//
//    }
//  }
//
//  // produces a list where all occurrences of the first given String are replaced
//  // by the second given String
//  public ILoString findAndReplace(String strOne, String strTwo) {
//    if (this.first.equals(strOne)) {
//      return new ConsLoString(strTwo, this.rest.findAndReplace(strOne, strTwo));
//    }
//    else {
//      return new ConsLoString(this.first, this.rest.findAndReplace(strOne, strTwo));
//    }
//  }
//
//  // produces a boolean that determines if the list is sorted in alphabetical
//  // order and is not case sensitive
//  public boolean isSorted() {
//    return this.rest.isSortedHelper(this.first) && this.rest.isSorted();
//  }
//
//  // checks if the current string is sorted.
//  public boolean isSortedHelper(String s) {
//    return this.first.toLowerCase().compareTo(s) >= 0 && this.rest.isSortedHelper(this.first);
//  }
//
//  // determines if the list has any duplicate strings
//  public boolean anyDupes() {
//    return this.rest.anyDupesHelper(this.first) || this.rest.anyDupes();
//  }
//
//  // takes in a string and checks if there are any other occurrences of the string
//  // in the list.
//  public boolean anyDupesHelper(String s) {
//    if (this.first.equals(s)) {
//      return true;
//    }
//    else {
//      return this.rest.anyDupesHelper(s);
//    }
//  }
//
//  // produces a list where the first, third, fifth... elements are from this list
//  // and
//  // the second, fourth, sixth... elements are from the given list
//  public ILoString interleave(ILoString givenList) {
//    return new ConsLoString(this.first, givenList.interleave(this.rest));
//  }
//
//  // takes this sorted list of string and a given sorted list of strings and
//  // produces a sorted
//  // list of strings that includes all items from lists including duplicates.
//  public ILoString merge(ILoString givenSortedList) {
//
//    return givenSortedList.mergeHelper(this.first, this.rest);
//
//  }
//
//  public ILoString mergeHelper(String s, ILoString givenSortedList) {
//    if (this.first.toLowerCase().compareTo(s.toLowerCase()) <= 0) {
//
//      return new ConsLoString(this.first, this.rest.mergeHelper(s, givenSortedList));
//
//    }
//    else {
//      return new ConsLoString(s, givenSortedList.merge(this));
//    }
//  }
//
//  // determines if this list contains pairs of identical strings. The first and
//  // second strings are the same,
//  // the third and fourth are the same, the fifth and sixth are the same, etc.
//  public boolean isDoubledList() {
//    return this.rest.isDoubledListAcc(this.first);
//  }
//
//  // helps check if the list is doubled. acc keeps track of the list so far
//  public boolean isDoubledListAcc(String acc) {
//    if (this.first.equals(acc)) {
//      return this.rest.isDoubledList();
//    }
//    else {
//      return false;
//    }
//  }
//
//  // returns this list of strings in reverse and combines them into one string
//  public String reverseConcat() {
//    return this.reverseAcc(new MtLoString()).combine();
//  }
//
//  // helps reverse the list. acc keeps track of the reverse list.
//  public ILoString reverseAcc(ILoString acc) {
//    return this.rest.reverseAcc(new ConsLoString(this.first, acc));
//  }
//
//  // determines if this list contains the same words reading the list in either
//  // order
//  public boolean isPalindromeList() {
//    return this.reverseAcc(new MtLoString()).interleave(this).isDoubledList();
//  }
//
//}
//
//class MtLoString implements ILoString {
//  MtLoString() {
//
//  }
//  /*
//   * TEMPLATE FIELDS:
//   * 
//   * 
//   * METHODS this.comebine() ... String this.sort() ... ILoString
//   * this.insert(String s) ... ILoString this.findAndReplace(String strOne, String
//   * strTwo) ... ILoString this.isSorted() ... boolean this.isSortedHelper(String
//   * s) ... boolean this.interleave(ILoString givenList) ... ILoString
//   * this.merge(ILoString givenSortedList) ... ILoString this.mergeHelper(String
//   * s, ILoString givenSortedList) ... ILoString this.isDoubledList() ... boolean
//   * this.isDoubledListAcc(String acc) ... acc this.reverseConcat() ... String
//   * this.reverseAcc(ILoString acc) ... ILoString this.isPalindromeList()...
//   * boolean
//   * 
//   * METHODS FOR FIELDS
//   * 
//   */
//
//  // combine all Strings in this list into one
//  public String combine() {
//    return "";
//  }
//
//  // Sorts the ILoString in alphabetical order and is not case sensative
//  public ILoString sort() {
//    return this;
//  }
//
//  // inserts the given string into a list of strings alphabetically sorted
//  public ILoString insert(String s) {
//    return new ConsLoString(s, this);
//  }
//
//  // produces a list where all occurrences of the first given String are replaced
//  // by the second given String
//  public ILoString findAndReplace(String strOne, String strTwo) {
//    return new MtLoString();
//  }
//
//  // produces a boolean that determines if the list is sorted in alphabetical
//  // order and is not case sensitive
//  public boolean isSorted() {
//    return true;
//  }
//
//  // checks if the current string is sorted.
//  public boolean isSortedHelper(String s) {
//    return true;
//  }
//
//  // produces a list where the first, third, fifth... elements are from this list
//  // and
//  // the second, fourth, sixth... elements are from the given list
//  public ILoString interleave(ILoString givenList) {
//    return givenList;
//  }
//
//  // takes this sorted list of string and a given sorted list of strings and
//  // produces a sorted
//  // list of strings that includes all items from lists including duplicates.
//  public ILoString merge(ILoString givenSortedList) {
//    return givenSortedList;
//  }
//
//  // helps merge two lists. s is the string from this list and givenSortedList is
//  // the sorted list that is given
//  public ILoString mergeHelper(String s, ILoString givenSortedList) {
//    return new ConsLoString(s, givenSortedList);
//  }
//
//  // determines if this list contains pairs of identical strings. The first and
//  // second strings are the same,
//  // the third and fourth are the same, the fifth and sixth are the same, etc.
//  public boolean isDoubledList() {
//    return true;
//  }
//
//  // helps check if the list is doubled. acc keeps track of the list so far
//  public boolean isDoubledListAcc(String acc) {
//    return false;
//  }
//
//  // returns this list of strings in reverse and combines them into one string
//  public String reverseConcat() {
//    return "";
//  }
//
//  // helps reverse the list. acc keeps track of the reverse list.
//  public ILoString reverseAcc(ILoString acc) {
//    return acc;
//  }
//
//  // determines if this list contains the same words reading the list in either
//  // order
//  public boolean isPalindromeList() {
//    return true;
//  }
//
//  // determines if the list has any duplicate strings
//  public boolean anyDupes() {
//    return false;
//  }
//
//  // takes in a string and checks if there are any other occurrences of the string
//  // in the list.
//  public boolean anyDupesHelper(String s) {
//    return false;
//  }
//
//}
//
//class ExamplesStrings {
//  // started code that contains the following example
//  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
//      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));
//
//  // represents examples for lists of string
//  ILoString mt = new MtLoString();
//  ILoString list1 = new ConsLoString("hi", this.mt);
//  ILoString list2 = new ConsLoString("Apple", list1);
//  ILoString list3 = new ConsLoString("acorn", list2);
//  ILoString list4 = new ConsLoString("coconut", list3);
//  ILoString list5 = new ConsLoString("zoro", list4);
//
//  // new list separate from the previous 5
//  ILoString list6 = new ConsLoString("luffy", this.mt);
//  ILoString list7 = new ConsLoString("zoro", list6);
//  ILoString list8 = new ConsLoString("doflamingo", list7);
//
//  // used this list to test for duplicates
//  ILoString list9 = new ConsLoString("doflamingo", list8);
//  ILoString dupes = new ConsLoString("Shanks", new ConsLoString("buggy",
//      new ConsLoString("kid", new ConsLoString("buggy", new MtLoString()))));
//
//  ILoString doubledList = new ConsLoString("hi", new ConsLoString("hi", new MtLoString()));
//  ILoString doubledHelper = new ConsLoString("hi",
//      new ConsLoString("bye", new ConsLoString("bye", new MtLoString())));
//
//  ILoString palidromeList = new ConsLoString("Adi",
//      new ConsLoString("is", new ConsLoString("cool", new ConsLoString("cool",
//          new ConsLoString("is", new ConsLoString("Adi", new MtLoString()))))));
//
//  // Test FindAndReplace method
//  boolean testFindAndReplace(Tester t) {
//    return t.checkExpect(this.list3.findAndReplace("Apple", "ball"),
//        new ConsLoString("acorn",
//            new ConsLoString("ball", new ConsLoString("hi", new MtLoString()))))
//        && t.checkExpect(this.list2.findAndReplace("hi", "goodbye"),
//            new ConsLoString("Apple", new ConsLoString("goodbye", new MtLoString())))
//        && t.checkExpect(this.list2.findAndReplace("nothing", "abcdef"),
//            new ConsLoString("Apple", new ConsLoString("hi", new MtLoString())))
//        && t.checkExpect(this.mt.findAndReplace("hi", "goodbye"), new MtLoString());
//  }
//
//  // Test anyDupes() method along with helpers
//  boolean testAnyDupes(Tester t) {
//    return t.checkExpect(this.list9.anyDupes(), true) && t.checkExpect(this.list5.anyDupes(), false)
//        && t.checkExpect(this.dupes.anyDupes(), true) && t.checkExpect(this.mt.anyDupes(), false)
//        && t.checkExpect(this.list8.anyDupesHelper("luffy"), true)
//        && t.checkExpect(this.list7.anyDupesHelper("okokok"), false)
//        && t.checkExpect(this.mt.anyDupesHelper("jam"), false);
//
//  }
//
//  // Test sort method(plus helper functions) and also the combine method that was
//  // in the starter code.
//  boolean testSort(Tester t) {
//    return t.checkExpect(this.mt.sort(), new MtLoString())
//        && t.checkExpect(this.list1.sort(), new ConsLoString("hi", this.mt))
//        && t.checkExpect(this.mary.combine(), "Mary had a little lamb.")
//        && t.checkExpect(this.mt.combine(), "") && t.checkExpect(this.list1.isSorted(), true)
//        && t.checkExpect(this.list4.isSorted(), false)
//        && t.checkExpect(this.list2.sort(),
//            new ConsLoString("Apple", new ConsLoString("hi", new MtLoString())))
//        && t.checkExpect(this.list3.sort(),
//            new ConsLoString("acorn",
//                new ConsLoString("Apple", new ConsLoString("hi", new MtLoString()))))
//        && t.checkExpect(this.list2.isSorted(), true) && t.checkExpect(this.mt.isSorted(), true);
//  }
//
//  // Test interleave()
//  boolean testInterleave(Tester t) {
//    return t.checkExpect(this.list4.interleave(list7),
//        new ConsLoString("coconut",
//            new ConsLoString("zoro",
//                new ConsLoString("acorn",
//                    new ConsLoString("luffy",
//                        new ConsLoString("Apple", new ConsLoString("hi", new MtLoString())))))))
//        && t.checkExpect(this.mt.interleave(list1), new ConsLoString("hi", new MtLoString()))
//        && t.checkExpect(this.list2.interleave(list1), new ConsLoString("Apple",
//            new ConsLoString("hi", new ConsLoString("hi", new MtLoString()))));
//  }
//
//  // Test merge and mergeHelper methods
//  boolean testMerge(Tester t) {
//    return t.checkExpect(this.list5.sort().merge(list8.sort()),
//        new ConsLoString("acorn",
//            new ConsLoString("Apple",
//                new ConsLoString("coconut", new ConsLoString("doflamingo", new ConsLoString("hi",
//                    new ConsLoString("luffy",
//                        new ConsLoString("zoro", new ConsLoString("zoro", new MtLoString())))))))))
//        && t.checkExpect(this.list1.mergeHelper("cool", list1), new ConsLoString("cool",
//            new ConsLoString("hi", new ConsLoString("hi", new MtLoString()))));
//  }
//
//  // Test reverseConcat() method
//  boolean testReverseConcat(Tester t) {
//    return t.checkExpect(this.list3.reverseConcat(), "hiAppleacorn")
//        && t.checkExpect(this.list2.reverseAcc(new MtLoString()),
//            new ConsLoString("hi", new ConsLoString("Apple", new MtLoString())))
//        && t.checkExpect(this.mt.reverseConcat(), "")
//        && t.checkExpect(this.mt.reverseAcc(new MtLoString()), new MtLoString());
//  }
//
//  // tests the isDoubledList method
//  boolean testIsDoubledList(Tester t) {
//    return t.checkExpect(this.doubledList.isDoubledList(), true)
//        && t.checkExpect(this.list9.isDoubledList(), false)
//        && t.checkExpect(this.mt.isDoubledList(), true)
//        && t.checkExpect(this.doubledHelper.isDoubledListAcc("hi"), true)
//        && t.checkExpect(this.mt.isDoubledListAcc(""), false);
//  }
//
//  // tests the isPalindromeList method
//  boolean testIsPalindromeList(Tester t) {
//    return t.checkExpect(this.palidromeList.isPalindromeList(), true)
//        && t.checkExpect(this.list5.isPalindromeList(), false)
//        && t.checkExpect(this.mt.isPalindromeList(), true);
//  }
//}
