/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 Bertrand Martel
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package fr.bmartel.speedtest.examples;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Download FTP example.
 *
 * @author Bertrand Martel
 */
public class DownloadFtpExample {

    /**
     * socket timeout used in ms.
     */
    private final static int SOCKET_TIMEOUT = 5000;

    /**
     * default ftp uri used for tests.
     */
    public final static String FTP_SERVER_URI = "ftp://speedtest.tele2.net/1MB.zip";

    /**
     * logger.
     */
    private final static Logger LOGGER = LogManager.getLogger(DownloadFtpExample.class.getName());

    /**
     * Download file example main.
     *
     * @param args no args required
     */
    public static void main(final String[] args) {

        // instantiate speed examples
        final SpeedTestSocket speedTestSocket = new SpeedTestSocket();

        //set timeout for download
        speedTestSocket.setSocketTimeout(SOCKET_TIMEOUT);

        // add a listener to wait for speed examples completion and progress
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(final SpeedTestReport report) {
                LogUtils.logFinishedTask(report.getSpeedTestMode(), report.getTotalPacketSize(),
                        report.getTransferRateBit(),
                        report.getTransferRateOctet(), LOGGER);

            }

            @Override
            public void onError(final SpeedTestError speedTestError, final String errorMessage) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("error " + speedTestError + " : " + errorMessage);
                }
            }

            @Override
            public void onProgress(final float percent, final SpeedTestReport downloadReport) {
                LogUtils.logSpeedTestReport(downloadReport, LOGGER);
            }

            @Override
            public void onInterruption() {
                //triggered when forceStopTask is called
            }
        });

        speedTestSocket.startDownload(FTP_SERVER_URI);
    }
}
