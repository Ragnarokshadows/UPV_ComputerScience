-- Exercise4.hs

module Exercise4 where

  import Stack
  
  fromList :: [a] -> (Stack a)
  fromList [] = empty
  fromList (x:xs) = (push x (fromList xs))


  toList :: (Stack a) -> [a]
  toList x
     | isEmpty x = []
     | otherwise = (top x):(toList (pop x))
