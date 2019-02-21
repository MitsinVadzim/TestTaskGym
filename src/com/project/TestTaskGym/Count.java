package com.project.TestTaskGym;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Count {
    void main(){
        List<W> weights = Stream.of(1, 2)
                .map(W::new)
                .collect(Collectors.toList());

        Map<Integer, List<List<W>>> arr = new HashMap<>();
        arr.put(0, Collections.singletonList(Collections.emptyList()));
        for (W weight : weights) {
            arr = arr.entrySet().stream()
                    .flatMap(x -> {
                                int newWeight = x.getKey() + weight.value;
                                List<List<W>> newList = x.getValue().stream()
                                        .map(ArrayList::new)
                                        .peek(y -> y.add(weight))
                                        .collect(Collectors.toList());
                                return Stream.of(
                                        new Pair<>(newWeight, newList),
                                        new Pair<>(x.getKey(), x.getValue())
                                );
                            }
                    )
                    .collect(Collectors.toMap(
                            Pair::getIndex,
                            Pair::getValue,
                            (x, y) -> {
                                List<List<W>> r = new ArrayList<>(x);
                                r.addAll(y);
                                return r;
                            }));
        }

        arr.entrySet().
                stream()
                .sorted(Comparator.comparing(Map.Entry::getKey, Comparator.reverseOrder()))
                .filter(x ->
                {
                    for (List<W> a : x.getValue()) {
                        for (List<W> b : x.getValue()) {
                            if(a == b) continue;
                            if (a.stream().noneMatch(b::contains)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(System.out::println);
    }


    static class Pair<T> {
        private final int index;
        final T value;

        Pair(int index, T value) {
            this.index = index;
            this.value = value;
        }

        int getIndex() {
            return index;
        }

        T getValue() {
            return value;
        }
    }

    static class W {
        final int value;

        W(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
}
