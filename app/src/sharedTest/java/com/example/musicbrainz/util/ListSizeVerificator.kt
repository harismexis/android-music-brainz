package com.example.musicbrainz.util

import org.junit.Assert

fun <T, P> verifyListsHaveSameSize(
    list0: List<T>,
    list1: List<P>
) {
    Assert.assertEquals(list0.size, list1.size)
}

fun <T> verifyListSize(
    expectedSize: Int,
    items: List<T>
) {
    Assert.assertEquals(expectedSize, items.size)
}

fun <T> verifyListSizeWhenAllIdsValid(items: List<T>, expectedListSize: Int) {
    verifyListSize(expectedListSize, items)
}

fun <T> verifyListSizeWhenSomeIdsAbsent(items: List<T>, expectedListSize: Int) {
    verifyListSize(expectedListSize, items)
}

fun <T> verifyListSizeWhenSomeItemsEmpty(items: List<T>, expectedListSize: Int) {
    verifyListSize(expectedListSize, items)
}

fun <T> verifyListSizeForNoData(items: List<T>, expectedListSize: Int) {
    verifyListSize(expectedListSize, items)
}