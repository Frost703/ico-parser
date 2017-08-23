package com.deloitte.projects.ico.parser;

import java.io.IOException;
import java.util.List;

public interface IcoParser<T> {
    List<T> parse() throws IOException;
}
