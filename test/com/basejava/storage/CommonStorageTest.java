package com.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ListStorageTest.class, ArrayStorageTest.class, SortedArrayStorageTest.class } )
public class CommonStorageTest {
}
