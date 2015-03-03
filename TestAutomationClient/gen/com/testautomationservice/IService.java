/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\workspace\\TestAutomationClient\\src\\com\\testautomationservice\\IService.aidl
 */
package com.testautomationservice;
public interface IService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.testautomationservice.IService
{
private static final java.lang.String DESCRIPTOR = "com.testautomationservice.IService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.testautomationservice.IService interface,
 * generating a proxy if needed.
 */
public static com.testautomationservice.IService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.testautomationservice.IService))) {
return ((com.testautomationservice.IService)iin);
}
return new com.testautomationservice.IService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_callServiceMethod:
{
data.enforceInterface(DESCRIPTOR);
this.callServiceMethod();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.testautomationservice.IService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void callServiceMethod() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_callServiceMethod, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_callServiceMethod = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void callServiceMethod() throws android.os.RemoteException;
}
