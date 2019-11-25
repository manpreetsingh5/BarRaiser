package com.aquamarine.barraiser.service.actions.interfaces;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface ActionService {
    Set<Map<String, Object>> getAllActions() throws IOException;
}
