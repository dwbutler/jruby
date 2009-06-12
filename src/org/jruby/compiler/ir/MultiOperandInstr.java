package org.jruby.compiler.ir;

// This is of the form:
//   v = OP(args, attribute_array); Ex: v = CALL(args, v2)

public class MultiOperandInstr extends IR_Instr
{
    public Operand[] _args;

    public class MultiOperandInstr(Operation opType, Variable result, Operand[] args)
    {
       _args = args;
       super(opType, result);
    }
}
