package com.zlx.plugin_lifecycle;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author gavin
 * @date 2019/2/18
 * lifecycle class visitor
 */
public class LifecycleClassVisitor extends ClassVisitor implements Opcodes {

    private String mClassName;

    public LifecycleClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("LifecycleClassVisitor : visit -----> started ：" + name);
        this.mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("LifecycleClassVisitor : visitMethod : " + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        //匹配FragmentActivity
        if ("androidx/fragment/app/FragmentActivity".equals(this.mClassName)) {
            System.out.println("LifecycleClassVisitor :FragmentActivity- change method ----> " + name);
            if ("onCreate".equals(name)) {
                //处理onCreate
                return new LifecycleOnCreateMethodVisitor(mv);
            } else if ("onDestroy".equals(name)) {
                //处理onDestroy
                return new LifecycleOnDestroyMethodVisitor(mv);
            }
        } else if ("androidx/fragment/app/Fragment".equals(this.mClassName)) {
            System.out.println("LifecycleClassVisitor :Fragment- change method ----> " + name);
            if ("onCreate".equals(name)) {
                //处理onCreate
                return new LifecycleOnCreateMethodVisitor(mv);
            } else if ("onDestroy".equals(name)) {
                //处理onDestroy
                return new LifecycleOnDestroyMethodVisitor(mv);
            }else if (name.equals("getBanner")){
                return new OnGetBannerMethodVisitor(mv);
            }
        } else if ("com/zlx/module_home/fragment/HomeFg".equals(this.mClassName)) {
            System.out.println("LifecycleClassVisitor :HomeFg- change method ----> " + name);
            if (name.equals("getBanner")){
                return new OnGetBannerMethodVisitor(mv);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        System.out.println("LifecycleClassVisitor : visit -----> end");
        super.visitEnd();
    }
}
