package ru.unn.agile.Deque.viewmodel;

import ru.unn.agile.Deque.model.Deque;

import java.util.List;

public class DequeViewModel {
    private final Deque<Integer> deque;

    private String inputNumber;
    private String output;
    private boolean isPushActionEnabled;
    private boolean isPopActionEnabled;
    private boolean isClearActionEnabled;
    private boolean isContainsActionEnabled;
    private boolean isDoActionButtonEnabled;

    private Action action;
    private IDequeLogger logger;

    public enum Action {
        PushFront("PushFront") {
            @Override
            public void doAction() {
                viewModel.deque.pushFront(Integer.valueOf(viewModel.inputNumber));
                viewModel.setPopClearCheckActionsEnabled(true);
            }

            @Override
            public boolean getEnabled() {
                return viewModel.isPushActionEnabled();
            }
        },
        PushBack("PushBack") {
            @Override
            public void doAction() {
                viewModel.deque.pushBack(Integer.valueOf(viewModel.inputNumber));
                viewModel.setPopClearCheckActionsEnabled(true);
            }

            @Override
            public boolean getEnabled() {
                return viewModel.isPushActionEnabled();
            }
        },
        PopFront("PopFront") {
            @Override
            public void doAction() {
                Integer value = viewModel.deque.popFront();
                if (viewModel.deque.isEmpty()) {
                    viewModel.setPopClearCheckActionsEnabled(false);
                }
                viewModel.output = value.toString();
            }

            @Override
            public boolean getEnabled() {
                return viewModel.isPopActionEnabled();
            }
        },
        PopBack("PopBack") {
            @Override
            public void doAction() {
                Integer value = viewModel.deque.popBack();
                if (viewModel.deque.isEmpty()) {
                    viewModel.setPopClearCheckActionsEnabled(false);
                }
                viewModel.output = value.toString();
            }

            @Override
            public boolean getEnabled() {
                return viewModel.isPopActionEnabled();
            }
        },
        Clear("Clear") {
            @Override
            public void doAction() {
                viewModel.deque.clear();
                viewModel.setPopClearCheckActionsEnabled(false);
            }

            @Override
            public boolean getEnabled() {
                return viewModel.isClearActionEnabled();
            }
        },
        Contains("Contains") {
            @Override
            public void doAction() {
                viewModel.output = String.valueOf(
                        viewModel.deque.contains(
                                Integer.valueOf(viewModel.inputNumber)));
            }

            @Override
            public boolean getEnabled() {
                return viewModel.isContainsActionEnabled();
            }
        };

        private static DequeViewModel viewModel;
        private String description;

        Action(final String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }

        public void setViewModel(final DequeViewModel viewModel) {
            Action.viewModel = viewModel;
        }

        public abstract void doAction();
        public abstract boolean getEnabled();

        public Object[] toArray() {
            return viewModel.deque.toArray();
        }

        public boolean isEmpty() {
            return viewModel.deque.isEmpty();
        }

        public int getSize() {
            return viewModel.deque.getSize();
        }
    }

    public enum LogMessages {
        ACTION_PERFORMED("Following action has occurred: "),
        ACTION_CHANGED("Action has been changed to: "),
        NUMBER_ENTERED("Following number has been entered: "),
        NAN_ENTERED("Following NaN has been entered: ");

        private String description;

        LogMessages(final String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public DequeViewModel() {
        deque = new Deque<>();
        action = Action.PushFront;
        action.setViewModel(this);
    }

    public DequeViewModel(final IDequeLogger logger) {
        this();
        if (logger == null) {
            throw new IllegalArgumentException("Logger can't be null");
        }
        this.logger = logger;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    private void setPopClearCheckActionsEnabled(final boolean isEnabled) {
        isPopActionEnabled = isEnabled;
        isClearActionEnabled = isEnabled;
        isContainsActionEnabled = isEnabled;

        updateDoActionButtonEnabled();
    }

    private void updateDoActionButtonEnabled() {
        isDoActionButtonEnabled = action.getEnabled();
    }

    public void setInputNumber(final String inputNumber) {
        this.inputNumber = inputNumber;
        try {
            Integer.parseInt(inputNumber);
            isPushActionEnabled = true;
            if (!deque.isEmpty()) {
                isContainsActionEnabled = true;
            }
        } catch (NumberFormatException e) {
            isPushActionEnabled = false;
            isContainsActionEnabled = false;
        }
        updateDoActionButtonEnabled();
    }

    public void logUpdatedInput() {
        try {
            Integer.parseInt(inputNumber);
            logger.log(LogMessages.NUMBER_ENTERED + inputNumber);
        } catch (NumberFormatException e) {
            logger.log(LogMessages.NAN_ENTERED + inputNumber);
        }
    }

    public void setAction(final int actionIndex) {
        Action action = Action.values()[actionIndex];

        setAction(action);
    }

    public void setAction(final Action action) {
        if (this.action != action) {
            logger.log(LogMessages.ACTION_CHANGED + action.toString());
        }

        this.action = action;

        updateDoActionButtonEnabled();
    }

    public Action getAction() {
        return action;
    }

    public String getOutput() {
        return output;
    }

    public boolean isPushActionEnabled() {
        return isPushActionEnabled;
    }

    public boolean isPopActionEnabled() {
        return isPopActionEnabled;
    }

    public boolean isClearActionEnabled() {
        return isClearActionEnabled;
    }

    public boolean isContainsActionEnabled() {
        return isContainsActionEnabled;
    }

    public boolean isDoActionButtonEnabled() {
        return isDoActionButtonEnabled;
    }

    public void doAction() {
        action.doAction();

        logger.log(LogMessages.ACTION_PERFORMED + action.toString());
    }
}
