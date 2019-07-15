-- *****************************
-- module Hello
-- *****************************

module Hello where
  
  -- right hello func:
  hello n = concat (replicate n "hello ")
  hello' n = putStrLn (concat (replicate n "hello \n"))
  
  
-- *****************************
-- module Signum
-- *****************************

module Signum where

  signum' x = if x < 0 then -1 else
              if x == 0 then 0 else 1


  signum'' x 
           | x < 0     = -1 
           | x == 0    = 0 
           | otherwise = 1 


-- *****************************
-- module Typeerrors
-- *****************************

module Typeerrors where

  convert :: (Char, Int) -> String
  convert (c,i) = [c] ++ show i

  -- main = convert (2,'c')
  main = convert ('c',2)


-- *****************************
-- module Length
-- *****************************

module Length where

  length' [] = 0
  length' (x:t) = 1 + length' t


-- *****************************
-- module Add
-- *****************************

module Add where

  add :: (Int , Int) -> Int
  add (x,y) = x + y

  cAdd :: Int -> Int -> Int
  cAdd x y = x + y

-- *****************************
-- module Power version 1
-- *****************************

module Power1 where

  power1 :: Int -> Int -> Int
  power1 _ 0 = 1
  power1 n t = n * power1 n (t - 1)

-- *****************************
-- module Power version 2
-- *****************************

module Power2 where

  power2 :: Int -> Int -> Int
  power2 _ 0 = 1
  power2 n t
         | even t = power2 (n * n) (div t 2)
         | otherwise = n * power2 (n * n) (div t 2)

-- *****************************
-- module Squarepow
-- *****************************

module Squarepow where

  squarepow :: Int -> Int
  squarepow x = x * x

  doubleHO :: (Int -> Int) -> Int -> Int
  doubleHO f x = f (f x)

  fourthpow :: Int -> Int
  fourthpow = doubleHO squarepow


-- *****************************
-- module Nextchar
-- *****************************

module Nextchar where

  import Data.Char 

  nextchar :: Char -> Char

  -- nextchar c = chr ((ord c) + 1)

  nextchar c
          | c == 'z' = 'a'
          | c == 'Z' = 'A'
          | otherwise = chr ((ord c) + 1)

-- *****************************
-- module Factorial
-- *****************************

module Factorial where

  fact :: Int -> Int
  fact 0 = 1
  fact n = n * fact (n - 1)
