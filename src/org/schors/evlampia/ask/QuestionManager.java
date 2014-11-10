package org.schors.evlampia.ask;

import java.util.HashMap;
import java.util.Map;

public class QuestionManager {
    private Map<String, Question> questions = new HashMap<>();

    public void addQuestion(Question question, String who) {
        questions.put(question.getId(), question);
    }

    public String openAnswer(String questionId, Answer answer) {
        Question q = questions.get(questionId);
        if (q == null) return "Неизвестный опрос: " + questionId;
        if (q.getType().equals(QuestionType.CLOSED)) return "Это закрытый опрос";
        q.addAnswer(answer);
        return null;
    }

    public String closedAnswer(String questionId, Answer answer) {
        Question q = questions.get(questionId);
        if (q == null) return "Неизвестный опрос: " + questionId;
        if (q.getType().equals(QuestionType.OPEN)) return "Это открытый опрос";
        q.addAnswer(answer);
        return null;
    }


}
