-- module Stack 
module Stack (Stack, empty, push, pop, top, isEmpty) where
data Stack a       = EmptyStack | Stk a (Stack a)
push x s           = Stk x s
top (Stk x s)      = x
pop (Stk _ s)      = s
empty              = EmptyStack
isEmpty EmptyStack = True
isEmpty (Stk x s)  = False

instance (Eq a) => Eq (Stack a) where
   EmptyStack == (Stk x y) = False
   (Stk x y) == EmptyStack = False
   EmptyStack == EmptyStack = True
   (Stk x y) == (Stk b c) = (x == b) && (y == c)

instance (Show a) => Show (Stack a) where
   show EmptyStack = "|"
   show (Stk x y) = (show x) ++ " <- " ++ (show y)

