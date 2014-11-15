/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
