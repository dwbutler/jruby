
package org.jruby.ext.ffi;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyModule;
import org.jruby.anno.JRubyClass;
import org.jruby.runtime.ObjectAllocator;

/**
 * Base pointer class for all JFFI pointers.
 */
@JRubyClass(name = "FFI::BasePointer", parent = "FFI::Pointer")
public class BasePointer extends Pointer {

    public static final String BASE_POINTER_NAME = "BasePointer";

    public static RubyClass createBasePointerClass(Ruby runtime, RubyModule module) {
        RubyClass result = module.defineClassUnder(BASE_POINTER_NAME,
                module.getClass("Pointer"),
                ObjectAllocator.NOT_ALLOCATABLE_ALLOCATOR);
        result.defineAnnotatedMethods(BasePointer.class);
        result.defineAnnotatedConstants(BasePointer.class);

        // Add Pointer::NULL as a constant
        module.getClass("Pointer").fastSetConstant("NULL", new BasePointer(runtime, new NullMemoryIO(runtime)));

        return result;
    }
    public static final RubyClass getBasePointerClass(Ruby runtime) {
        return runtime.fastGetModule("FFI").fastGetClass(BASE_POINTER_NAME);
    }
    public BasePointer(Ruby runtime, DirectMemoryIO io) {
        super(runtime, getBasePointerClass(runtime), io);
    }
    public BasePointer(Ruby runtime, DirectMemoryIO io, long size) {
        super(runtime, getBasePointerClass(runtime), io, size);
    }
    public BasePointer(Ruby runtime, DirectMemoryIO io, long size, int typeSize) {
        super(runtime, getBasePointerClass(runtime), io, size, typeSize);
    }
//    BasePointer(Ruby runtime, long address) {
//        super(runtime, getRubyClass(runtime),
//                address != 0 ? new NativeMemoryIO(address) : new NullMemoryIO(runtime));
//    }
    public BasePointer(Ruby runtime, RubyClass klass, DirectMemoryIO io, long size) {
        super(runtime, klass, io, size);
    }
    public BasePointer(Ruby runtime, RubyClass klass, DirectMemoryIO io, long size, int typeSize) {
        super(runtime, klass, io, size, typeSize);
    }

    
    @Override
    protected final AbstractMemory slice(Ruby runtime, long offset) {
        return new BasePointer(runtime, getBasePointerClass(runtime),
                (DirectMemoryIO) getMemoryIO().slice(offset), 
                size == Long.MAX_VALUE ? Long.MAX_VALUE : size - offset, typeSize);
    }

    protected BasePointer getPointer(Ruby runtime, long offset) {
        return new BasePointer(runtime, getMemoryIO().getMemoryIO(offset), Long.MAX_VALUE);
    }
}