-- Exercise2.hs
import qualified Circle
import qualified Triangle

main = do 
  putStrLn ("The area is of the Circle is " ++ show (Circle.area 2))
  putStrLn ("The area is of the Triangle is " ++ show (Triangle.area 4 5))
