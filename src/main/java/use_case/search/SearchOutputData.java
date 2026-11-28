package use_case.search;

import java.util.List;
import java.util.Map;

public class SearchOutputData {
    private final List<Map<String, Object>> result;

    public SearchOutputData(List<Map<String, Object>> result) {
        this.result = result;
    }

    public List<Map<String, Object>> getResult(){
        return this.result;
    }
}
