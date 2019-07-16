-- *****************************
-- module Practice5
-- *****************************

module Practice5_2 where

  type Person = String
  type Book = String
  type Database = [(Person,Book)]

  exampleBase :: Database
  exampleBase = [("Alicia","El nombre de la rosa"),
                 ("Juan","La hija del canibal"),("Pepe","Odesa"),
                 ("Alicia","La ciudad de las bestias")]

  obtain :: Database -> Person -> [Book]
  obtain dBase thisPerson
    = [book | (person,book) <- dBase, person == thisPerson]

  borrow :: Database -> Book -> Person -> Database
  borrow dBase thisBook thisPerson
    = dBase ++ [(thisPerson,thisBook)]

  return' :: Database -> (Person,Book) -> Database
  return' dBase (thisPerson,thisBook)
    = [(person,book) | (person,book) <- dBase, person /= thisPerson && 
      book /= thisBook]

  --data TreeInt = Leaf Int | Branch TreeInt TreeInt
  data Tree a = Leaf a | Branch (Tree a) (Tree a) deriving Show
  data BinTreeInt = Void | Node Int BinTreeInt BinTreeInt deriving Show

  symmetric :: Tree a -> Tree a 
  symmetric (Leaf x) = (Leaf x)
  symmetric (Branch a b) = (Branch (symmetric b) (symmetric a))

  listToTree :: [a] -> Tree a
  listToTree [x] = (Leaf x)
  listToTree (x:xs) = (Branch (listToTree [x]) (listToTree (xs)))

  treeToList :: Tree a -> [a]
  treeToList (Leaf x) = [x]
  treeToList (Branch a b) = (treeToList a) ++ (treeToList b)
  
  insTree :: Int -> BinTreeInt -> BinTreeInt
  insTree x (Void) = (Node x Void Void)
  insTree x (Node y a b)
          | x <= y = (Node y (insTree x a) b)
          | otherwise = (Node y a (insTree x b))
  
  creaTree :: [Int] -> BinTreeInt
  creaTree [x] = (Node x Void Void)
  creaTree (x:y:xs) 
             | x <= y = (Node x Void (creaTree (y:xs)))
             | otherwise = (Node x (creaTree (y:xs)) Void)

  treeElem :: Int -> BinTreeInt -> Bool
  treeElem x (Void) = False
  treeElem x (Node y a b)
           | x == y = if x==y then True else False
           | x <= y = treeElem x a
           | otherwise = treeElem x b     
