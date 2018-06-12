val s = Stream.from(1)

s.take(2)
s.take(2)

val itr = s.iterator

val r = itr.take(6)

val t = itr.take(4)

r.sum

t.sum

var i = (1 to 100).iterator
for {
  a <- 1 to 10
  c = i.next()
} yield (c, a)









