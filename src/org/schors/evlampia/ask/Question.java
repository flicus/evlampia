package org.schors.evlampia.ask;

import java.util.HashMap;
import java.util.Map;

public class Question {
    private String id;
    private String owner;
    private String title;
    private QuestionType type;
    private Map<String, AnswerOption> answerOptions = new HashMap<>();
    private Map<String, Answer> answers = new HashMap<>();
    private int reminder = 0;

    public Question() {
    }

    public Question(String id, String owner, String title, QuestionType type) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.type = type;
    }

    public Question(String id, String owner, String title, QuestionType type, int reminder) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.type = type;
        this.reminder = reminder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Map<String, AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public Map<String, Answer> getAnswers() {
        return answers;
    }

    public Question addOption(AnswerOption option) {
        answerOptions.put(option.getId(), option);
        return this;
    }

    public Question addAnswer(Answer answer) {
        answers.put(answer.getWho(), answer);
        return this;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", answerOptions=" + answerOptions +
                ", answers=" + answers +
                '}';
    }
}
