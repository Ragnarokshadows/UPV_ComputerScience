-- *****************************
-- module Practice5
-- *****************************

module Practice5 where 

  divisors :: Int -> [Int]
  divisors n = [i | i<-[1..n],n `mod` i==0]
  
  member :: Int -> [Int] -> Bool
  member y xs = not (null [() | x<-xs, y==x])

  isPrime :: Int -> Bool
  isPrime n = length (divisors n)==2 || length (divisors n)==1

  primes :: Int -> [Int]
  primes n = take n [i | i<-[1..], isPrime i] 

  selectEven :: [Int] -> [Int]
  selectEven xs = [i | i<-xs, i `mod` 2==0] 

  selectEvenPos :: [Int] -> [Int]
  selectEvenPos [] = []
  selectEvenPos [x] = [x]
  selectEvenPos (x:_:xs) = x : selectEvenPos xs 

  selectEvenPos2 :: [Int] -> [Int]
  selectEvenPos2 x = map (x !!) [0,2..(length x)-1]  

  iSort :: [Int] -> [Int]
  iSort [] = []
  iSort (x:xs) = ins x (iSort xs)

  ins :: Int -> [Int] -> [Int]
  ins x [] = [x]
  ins x (y:ys)
    | x<=y = (x:y:ys)
    | otherwise = y : (ins x ys)

  doubleAll :: [Int] -> [Int]
  doubleAll xs = map (*2) xs

  map' :: (a -> b) -> [a] -> [b]
  map' f (x:xs) = f x:map' f xs

  filter' :: (a -> Bool) -> [a] -> [a]
  filter' p [] = []
  filter' p (x:xs) = if p x then x:filter' p xs else filter' p xs
