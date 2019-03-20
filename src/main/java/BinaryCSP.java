import org.jetbrains.annotations.NotNull;

import java.util.List;

class BinaryCSP {
    private final List<Variable> variables;
    private final List<Constraint> constraints;

    public BinaryCSP(List<Variable> variables, List<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    @NotNull
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(this.variables.size());

        for (Variable variable : this.variables) {
            result.append(variable.getDomain().toSimpleString()).append("\n");
        }

        for (Constraint constraint : this.constraints) {
            result.append(constraint);
        }

        return result.toString();
    }
}
