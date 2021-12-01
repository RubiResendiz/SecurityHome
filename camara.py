from asyncio.coroutines import iscoroutinefunction
import cv2
import socket
import time
import imutils
import base64
from pyzbar import pyzbar
from firebase import firebase
from datetime import datetime
import asyncio
import queue
import threading as th
import netifaces as ni
import json
from playsound import playsound

q = queue.Queue(maxsize=1)

def contador():
    playsound("ok.wav")
    time.sleep(1)
    print("Finished")
    
firebase = firebase.FirebaseApplication('https://security-home-b0ecc-default-rtdb.firebaseio.com/', None) 

def read_barcodes(frame, isR):
    barcodes = pyzbar.decode(frame)
    for barcode in barcodes:
        x, y , w, h = barcode.rect
        #PASO 1 DEFINIR CONTORNO Y LEER EN UN RECTÁNGULO
        barcode_info = barcode.data.decode('utf-8')
        print(isR)
        if q.empty():
            t = th.Timer(3000,contador())
            q.put(t)
            tx = q.get()
            tx.start()
            now = datetime.now()
            actual = now.strftime("%Y-%m-%d %H:%M:%S")
            day = now.strftime("%Y-%m-%d")
            hour = ("%s:%s"%(now.hour,now.minute))
            
            if(len(barcode_info)==28):
                print("Residente " + barcode_info)
                dir = "/residentes/" + barcode_info
                result = firebase.get(dir, '')
                if not result is None:  
                    data = {'id_visitante': barcode_info,'tiempo': actual,'dia': day, 'hora': hour}
                    firebase.post('/visitas/',data)
                else:
                    print("No existe en la BD")

            if len(barcode_info)==38:
                print("Invitado " + barcode_info)
                dir = "/residentes/" + barcode_info
                result = firebase.get(dir, '')
                if not result is None:
                    data = {'id_visitante': barcode_info,'tiempo': now.strftime("%Y-%m-%d %H:%M:%S"),'dia': now.strftime("%Y-%m-%d"), 'hora': ("%s:%s"%(now.hour,now.minute))}
                    firebase.post('/visitas/',data)
            if len(barcode_info)==15:
                print(barcode_info)
                dir = "/reuniones/" + barcode_info
                result = firebase.get(dir, '')
                if result is None:
                    print("No hay registro")  
                else:
                    json_str = json.dumps(result)
                    resp = json.loads(json_str)
                    fechainicio = datetime.strptime(resp['fechainicio'], "%Y-%m-%d")
                    fechafin = datetime.strptime(resp['fechafin'], "%Y-%m-%d")
                    horainicio = datetime.strptime(resp['horainicio'], '%H:%M').time()
                    horafin = datetime.strptime(resp['horafin'], '%H:%M').time()
                    comparadia =  datetime.strptime(day, "%Y-%m-%d")
                    comparahora = datetime.strptime(hour, '%H:%M').time()
                    print(type(comparadia))
                    if comparadia >= fechainicio and comparadia<=fechafin:
                        print("dias sí")
                        if comparadia == fechainicio:
                            if(comparahora >=horainicio):
                                print("fiestaloca")
                            else:
                                print("No empieza la fiesta :(")
                        else:
                            print((fechafin-comparadia))
                            if (fechafin-comparadia) ==  relativedelta:
                                if comparahora<=horafin:
                                    print("si hay fiestaaa")
                                else:
                                    print("se acabo la hora :(")
                            else:
                                print("fiesta a lo lardo del dia")
                    else:
                        print("dias no")
                        print(fechainicio)
                        print(comparadia)
                    
                    
        else:
            print("deben pasar 3 segundo mínimamente")   
            
        cv2.rectangle(frame, (x, y),(x+w, y+h), (0, 255, 0), 2)
        #PASO 2 MAPEAR CON UNA FUENTE Y RETORNAR FRAME
        font = cv2.FONT_HERSHEY_DUPLEX
        cv2.putText(frame, barcode_info, (x + 6, y - 6), font, 2.0, (255, 255, 255), 1)

    return frame

def main():
    isRun = False
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    #Establecemos la entrada de video
    vid = cv2.VideoCapture(-1,cv2.CAP_V4L)
    FPS = vid.get(cv2.CAP_PROP_FPS)
    totalNoFrames = int(vid.get(cv2.CAP_PROP_FRAME_COUNT))
    durationInSeconds = float(totalNoFrames) / float(FPS)
    d=vid.get(cv2.CAP_PROP_POS_MSEC)
    fps,st,frames_to_count,cnt = (0,0,1,0)

    global TS
    #Establecemos la IP del servidor (este equipo)

    ni.ifaddresses('wlan0')
    ip = ni.ifaddresses('wlan0')[ni.AF_INET][0]['addr']
    host_ip = ip
    print('IP DEL HOST:', host_ip)
    #El puerto será 9999
    port = 9999
    #unimos los valores del IP y HOST del servidor
    socket_address = (host_ip, port)
    # Instalamos el servidor en el socket
    server_socket.bind(socket_address)
    # COMENZAMOSD A ESCUCHAR EL PUERTO 9999
    server_socket.listen(5)
    print("LUGAR DE ESCUCHA: ", socket_address)
    try:
        while True:    
            #CUANDO SE ACEPTE LA CONEXIÓN, SE ABRIRÁ EL SERVICIO DE STREAMING
            client_socket, addr = server_socket.accept()
            print('CONEXIÓN ENTRANTE:', addr)
            WIDTH = 400
            while True:
                try:
                    #CAPTURAMOS FOTOGRAMA
                    _,img = vid.read()
                    frame = imutils.resize(img,width=WIDTH)
                    #ENVIAMOS FOTOGRAMA PARA PROCESAR POSIBLE QR
                    frame = read_barcodes(frame, isRun)
                    #CODIFICAMOS COMO IMAGEN
                    frame = cv2.putText(frame,'FPS: '+str(round(fps,1)),(10,40),cv2.FONT_HERSHEY_SIMPLEX,0.7,(0,0,255),2)
                    if cnt == frames_to_count:
                        try:
                            fps = (frames_to_count/(time.time()-st))
                            st=time.time()
                            cnt=0
                            if fps>FPS:
                                TS+=0.001
                            elif fps<FPS:
                                TS-=0.001
                            else:
                                pass
                        except:
                            pass
                    cnt+=1
                    #MOSTRAMOS EN PANTALLA
                    encoded,buffer = cv2.imencode('.jpeg',frame,[cv2.IMWRITE_JPEG_QUALITY,80])
                    title = ("SECURITY HOME FROM TO " + addr[0])
                    cv2.imshow(title, frame)
                
                    #CODIFICAMOS COMO MENSAJE DE BASE 64
                    message = base64.b64encode(buffer)
                    size = len(message)
                    #print(size)
                    strSize = str(size) + "\n"
                    #MANDAMOS 2 SEPARADORES Y EL MENSAJE FINAL(FOTOGRAMA CODIFICADO)
                    client_socket.sendto(strSize.encode('utf-8'),addr)
                    client_socket.sendto(message,addr)
                    client_socket.sendto(("\ncaritafacherafacherita\n").encode('utf-8'),addr)
                except Exception as e:
                    print(e)
                    raise Exception(e)
                if cv2.waitKey(1) == 27:
                    break
            cv2.destroyAllWindows()
    except KeyboardInterrupt:
        
        print('Finalizado')

if __name__ == '__main__':
    main()